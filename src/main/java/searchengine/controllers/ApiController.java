package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.IndexService;
import searchengine.services.StatisticsService;

@RestController
@RequestMapping("/api")
public class ApiController {

@Autowired
    private final StatisticsService statisticsService;
@Autowired
//private IndexServiceRepositories indexServiceRepositories;*/
private IndexService indexService;


    public ApiController(StatisticsService statisticsService, IndexService indexService) {
        this.statisticsService = statisticsService;
        this.indexService = indexService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
//    *****************  This is my addition / Valentina/

 @GetMapping("/startIndexing")
    public ResponseEntity startIndexing() {
     return ResponseEntity.ok(indexService.startIndexing());

}

    @GetMapping("/stopIndexing")
    public ResponseEntity stopIndexing() {

        return ResponseEntity.ok(indexService.stopIndexing());
    }

//    ******************************************************

}
