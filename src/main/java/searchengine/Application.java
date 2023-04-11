package searchengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.model.PageRepository;
import searchengine.model.SiteRepository;
import searchengine.services.ForkJoinIndex;
import searchengine.services.IndexingSite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);



        /*****************  This is my addition / Valentina ************************/
//      List<String> list = new ArrayList<>();
//      list.add("https://www.lenta.ru");
//      list.add("https://www.skillbox.ru");
//      list.add("https://www.playback.ru");
//
//        List<IndexingSite>   result= new ForkJoinPool().invoke(new ForkJoinIndex(list));
//        result.forEach(e-> System.out.println(e));


    }
}