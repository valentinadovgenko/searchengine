package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.Repositories.PageRepository;
import searchengine.Repositories.SiteRepository;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.model.Page;
import searchengine.model.StatusList;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

@Service
public class IndexService {
    @Autowired
    private SitesList sites;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;

    public String startIndexing() {

        String result = "";
        List<Site> listSites = sites.getSites();

        for ( int i = 0; i < listSites.size(); i++ ) {
            String path = listSites.get(i).getUrl();
            String name = listSites.get(i).getName();
            String lastError = "";
            Date statusDate = new Date();
            System.out.println(name + " **** " + path + " **** " + statusDate);

            //  Проверяем наличие сайта и если есть такой в таблицах удаляяем

            searchengine.model.Site currentSite = siteRepository.findByName(name);

            if (currentSite != null) {
                if (currentSite.getStatus() == StatusList.INDEXING) {
                    result = result + " result : false" + "\n" + "error : Индексация уже запущена" + "\n";
                    continue;
                } else {
                    System.out.println(currentSite.getId() + " record is deleted");
                    pageRepository.deleteBySite(currentSite);
                    siteRepository.deleteById(currentSite.getId());
                }
            }
            //  Добавляем запись в таблицу Site
            System.out.println("Adding records");
            searchengine.model.Site siteTable =
                    new searchengine.model.Site(StatusList.INDEXING, statusDate, lastError, path, name);
            siteRepository.save(siteTable);

            currentSite = siteRepository.findByName(name);

            // Запускаим Fork Join
            IndexingSite.clearFinalSetForPage();
            LinkedHashSet<PageLink> links = new ForkJoinPool().invoke(new IndexingSite(path));
            System.out.println("Set from class PageLink is ready for writing to table!   " + new Date());

            //  Добавляем запись в таблицу Page
            currentSite.setStatusTime(new Date());
            siteRepository.save(siteTable);
            if(!links.isEmpty()) {
                for ( PageLink p : links ) {
                    Page pageTable = new Page(currentSite, p.getUrl(), p.getCode(), p.getContent());
                    pageRepository.save(pageTable);
                }
                currentSite.setStatus(StatusList.INDEXED);
                currentSite.setStatusTime(new Date());

            } else {
                currentSite.setStatus(StatusList.FAILED);
                currentSite.setStatusTime(new Date());
                currentSite.setLastError("ForkJoin has not worked");
            }
            siteRepository.save(currentSite);
            result = result + "result : true" + "\n";
        }
        return result;
    }


    public String stopIndexing() {
        String result = "Indexing was stoped user!" + "\n";
        if (siteRepository != null) {
            for ( searchengine.model.Site recordSite : siteRepository.findAll() ) {
                if (recordSite.getStatus().equals(StatusList.INDEXING)) {
                    recordSite.setStatus(StatusList.FAILED);
                    recordSite.setStatusTime(new Date());
                    recordSite.setLastError("Indexing was stoped user!");
                    siteRepository.save(recordSite);
                    Thread.interrupted();
                    result = result + "result: true" + "\n";
                } else {
                    result = result + " result : false" + "\n" + "error : Индексация не запущена" + "\n";
                }
            }
        }
        return result;
    }

}
