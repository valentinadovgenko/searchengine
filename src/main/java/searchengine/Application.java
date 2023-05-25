package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        /*****************  This is my addition / Valentina ************************/
//        Демонстрация ForkJoin

//
//        String url="https://www.skillbox.ru";
//
//
//
//
//        LinkedHashSet<PageLink> listPageLink = new ForkJoinPool().invoke(new IndexingSite(url));
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