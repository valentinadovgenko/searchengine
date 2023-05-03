package searchengine;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.jsoup.Jsoup;
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
import searchengine.services.PageLink;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;


@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        /*****************  This is my addition / Valentina ************************/
//        Демонстрация ForkJoin

//        List<String> list = new ArrayList<>();
//        list.add("https://www.lenta.ru");
//        list.add("https://www.skillbox.ru");
//        list.add("https://www.playback.ru");
//
//        List<IndexingSite> result = new ForkJoinPool().invoke(new ForkJoinIndex(list));
//
//        try {
//            PrintWriter writer = new PrintWriter("result.txt");
//            for ( IndexingSite vector : result ) {
//                for ( PageLink item : vector.getVector() ) {
//                    writer.write("page.path: "+item.getValue() + "\n");
//                    writer.write("  page.code: "+ item.getCode() + "\n");
//                    writer.write("      page.content: "+item.getContent() + "\n");
//                }
//            }
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

}