package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchengine.services.FakeUserAgent;
import searchengine.services.IndexingSite;
import searchengine.services.PageLink;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        /*****************  This is my addition / Valentina ************************/
//        Демонстрация ForkJoin


//        String url="https://www.playback.ru";
//        Long start = System.currentTimeMillis();
//        ForkJoinPool pool =ForkJoinPool.commonPool();
//
//        LinkedHashSet<PageLink> listPageLink = pool.invoke(new IndexingSite(url));
//        IndexingSite.stop();
//
//        System.out.println(System.currentTimeMillis()-start);
//
//        try {
//            PrintWriter writer = new PrintWriter("result.txt");
//            StringBuilder builder = new StringBuilder();
//                for ( PageLink item : listPageLink)  {
//                    builder.append("page.path: "+item.getUrl()+ "\n");
//                    builder.append("  page.code: "+ item.getCode() + "\n");
//                    builder.append("**********************************************************************" + "\n");
//                }
//            writer.write(builder.toString());
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }
}