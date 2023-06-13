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
public class IndexService extends Thread {
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

            if (!IndexingSite.isStopIndexing()) {
                String path = listSites.get(i).getUrl();
                String name = listSites.get(i).getName();
                String lastError = "";
                Date statusDate = new Date();
                System.out.println(name + " **** " + path + " **** " + statusDate);

                //  Проверяем наличие сайта и если есть такой в таблицах удаляяем записи

                searchengine.model.Site currentSite = siteRepository.findByName(name);

                if (currentSite != null) {
                    if (currentSite.getStatus() == StatusList.INDEXING) {
                        result = result + " false" + "\n" + "error : Индексация уже запущена" + "\n";
                        break;
                    } else {
                        pageRepository.deleteBySite(currentSite);
//                    siteRepository.deleteById(currentSite.getId());
                    }
                } else {
                    //  Добавляем запись в таблицу Site
                    System.out.println("Adding records");
                    searchengine.model.Site siteTable =
                            new searchengine.model.Site(StatusList.INDEXING, statusDate, lastError, path, name);
                    siteRepository.save(siteTable);
                    currentSite = siteRepository.findByName(name);
                }

                // Запускаим Fork Join

                IndexingSite.clearFinalSetForPage();
                ForkJoinPool pool = new ForkJoinPool();
                LinkedHashSet<PageLink> links = pool.invoke(new IndexingSite(path));
                if (!links.isEmpty()) {
                    currentSite.setStatus(StatusList.INDEXED);
                    addPageTable(links, currentSite);
                } else {
                    currentSite.setStatus(StatusList.FAILED);
                    currentSite.setLastError("ForkJoin has not worked");
                }
                currentSite.setStatusTime(new Date());
                siteRepository.save(currentSite);
            } else {
                break;
            }
            result = result + "true " + "\n";
        }
        return result;
    }

    private void addPageTable(LinkedHashSet<PageLink> links, searchengine.model.Site currentSite) {
        System.out.println("Set from class PageLink is ready for writing to table!   " + new Date());
        List<Page> pageList = new ArrayList<>();
        for ( PageLink p : links ) {
            pageList.add(new Page(currentSite, p.getUrl(), p.getCode(), p.getContent()));
        }
        pageRepository.saveAll(pageList);
    }

    public String stopIndexing() {
        IndexingSite.setStopIndexing(true);
        boolean check = false;
        Iterable<searchengine.model.Site> siteList = siteRepository.findAll();
        for ( searchengine.model.Site recordSite : siteList ) {
            if (recordSite.getStatus().equals(StatusList.INDEXING)) {
                recordSite.setLastError("Indexing was stopped user!");
                recordSite.setStatus(StatusList.FAILED);
                recordSite.setStatusTime(new Date());
                check = true;
            }
        }
        siteRepository.saveAll(siteList);
        if (check) {
            return "true";
        } else {
            IndexingSite.setStopIndexing(false);
            return "false, " + "error: Индексация не запущена";
        }
    }

}
