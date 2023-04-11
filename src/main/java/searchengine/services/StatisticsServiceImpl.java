package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.model.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final Random random = new Random();
    private final SitesList sites;

    //   ************************* My code ********************
    @Autowired
    private final SiteRepository siteRepository;
    @Autowired
    private final PageRepository pageRepository;

    @Override
    public List<PageTable> getAllPage() {
        return (List<PageTable>) pageRepository.findAll();
    }

    @Override
    public List<SiteTable> getAllSite() {
        return (List<SiteTable>) siteRepository.findAll();
    }

    @Override
    public void deletePage(int id) {

        pageRepository.deleteById(id);
    }

    @Override
    public void deletePageAll() {
        pageRepository.deleteAll();
    }
    @Override
    public void deleteSiteAll() {
        siteRepository.deleteAll();
    }


    @Override
    public void deleteSite(int id) {
        siteRepository.deleteById(id);
    }

    @Override
    public void startIndexing() {

        List<Site> list = sites.getSites();

        for ( int i = 0; i < list.size(); i++ ) {
            String path = list.get(i).getUrl();
            String name = list.get(i).getName();
            String lastError = "";
            Date statusDate = new Date();
            System.out.println( name + " **** " + path + " **** " + statusDate);

            SiteTable currentSite = siteRepository.findByName(name);
            System.out.println(currentSite.getId() + "|||" + currentSite.getStatus());
            if (currentSite != null) {
                pageRepository.deleteBySite(currentSite);
                siteRepository.deleteById(currentSite.getId());
            }


            SiteTable siteTable = new SiteTable(StatusList.INDEXING, statusDate, lastError, path, name);
            siteRepository.save(siteTable);
             currentSite = siteRepository.findByName(name);
            createPage(currentSite);
        }
    }

    private void createPage(SiteTable siteTable) {
        IndexingSite indexingSite = new IndexingSite(siteTable.getUrl());
        Set<PageLink> links = indexingSite.getVector();
        for(PageLink p: links) {
            PageTable pageTable = new PageTable(siteTable, p.getValue(), p.getCode(), p.getContent());
            pageRepository.save(pageTable);
        }
        siteTable.setStatus(StatusList.INDEXED);
        siteRepository.save(siteTable);
    }


//    ****************************

    @Override
    public StatisticsResponse getStatistics() {
        String[] statuses = {"INDEXED", "FAILED", "INDEXING"};
        String[] errors = {
                "Ошибка индексации: главная страница сайта не доступна",
                "Ошибка индексации: сайт не доступен",
                ""
        };

        TotalStatistics total = new TotalStatistics();
        total.setSites(sites.getSites().size());
        total.setIndexing(true);

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<Site> sitesList = sites.getSites();
        for ( int i = 0; i < sitesList.size(); i++ ) {
            Site site = sitesList.get(i);
            DetailedStatisticsItem item = new DetailedStatisticsItem();
            item.setName(site.getName());
            item.setUrl(site.getUrl());
            int pages = random.nextInt(1_000);
            int lemmas = pages * random.nextInt(1_000);
            item.setPages(pages);
            item.setLemmas(lemmas);
            item.setStatus(statuses[i % 3]);
            item.setError(errors[i % 3]);
            item.setStatusTime(System.currentTimeMillis() -
                    (random.nextInt(10_000)));
            total.setPages(total.getPages() + pages);
            total.setLemmas(total.getLemmas() + lemmas);
            detailed.add(item);
        }

        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }
}
