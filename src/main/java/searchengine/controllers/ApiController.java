package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.PageRepository;
import searchengine.model.PageTable;
import searchengine.model.SiteTable;
import searchengine.services.StatisticsService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

@Autowired
    private final StatisticsService statisticsService;

    public ApiController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
//    *****************  This is my addition / Valentina/

    @GetMapping("/getPageAll")  //    return table page all records
    public String getPage() {
        Iterable<PageTable> pages = statisticsService.getAllPage();
        String result = "";
        for ( PageTable p : pages ) {
            result = result.concat("\n").concat(p.getPathText());
        }
        return result;
    }
 @GetMapping("/startIndexing") //    return table site all records
    public ResponseEntity startIndexing() {
        String result=" YES";
        statisticsService.startIndexing();
     return new ResponseEntity<>(result, HttpStatus.OK);

//        Iterable<SiteTable> siteTables =statisticsService.getAllSite();
//        for (SiteTable s: siteTables) {
//            if(s.getUrl().equals(path)) {
//                int siteId=s.getId();
//                result= String.valueOf(s.getId());
//                Iterable<PageTable> pageTables = statisticsService.getAllPage();
//                for (PageTable p:pageTables) {
//                    result = result + String.valueOf(p.getId()) + " / " + p.getSite().getName()+"\n";
//
//                }
//            }
//        }
}

//    ******************************************************

}
