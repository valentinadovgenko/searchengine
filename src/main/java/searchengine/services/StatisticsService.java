package searchengine.services;

import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.PageTable;
import searchengine.model.SiteTable;

import java.util.List;

public interface StatisticsService {


    void deletePage(int id);


    void deletePageAll();

    void deleteSiteAll();

    void deleteSite(int id);

    String startIndexing();

    StatisticsResponse getStatistics();


}
