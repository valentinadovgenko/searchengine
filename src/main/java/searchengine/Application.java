package searchengine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.services.IndexingSite;

import javax.print.attribute.standard.Sides;
import java.util.HashMap;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        /*****************  This is my addition / Valentina ************************/

//        IndexingSite indexingSite = new IndexingSite("https://www.playback.ru");
//        System.out.println(indexingSite.print());
    }
}