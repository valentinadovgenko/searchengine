package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.model.*;
import searchengine.services.IndexingSite;

import java.util.Date;

/*****************  This is my addition / Valentina ************************/
@RestController
public class IndexController {
    @Autowired
    private  SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/api/startIndexing")
    public String startIndexing() {
//  Вызываем метот индексации кот. находится в serviсe и возвращаю список всех сайтов."
//  Сам рабочий сайт прописан внутри метода, я не знаю как его достать из конфигурации

        String path ="https://www.playback.ru";
        String name = "playback.ru";

        IndexingSite indexingSite = new IndexingSite(path);
    // Удаляем все имеющиеся записи
        pageRepository.deleteAll();
        siteRepository.deleteAll();
    // Добавляем запись в таблицу site
        SiteTable table = new SiteTable();
        table.setName(name);
        table.setUrl(path);
        table.setStatus(StatusList.INDEXING);
        table.setStatusTime(new Date());
        siteRepository.save(table);
        // Добавляем запись в таблицу page
        for (String item:indexingSite.getList()) {
            PageTable tablePage = new PageTable(table,item,200,"NO");
            pageRepository.save(tablePage);
        }
        return indexingSite.print();
    }


}
