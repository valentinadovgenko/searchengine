package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class IndexingSite {
// TODO: Как взять из конфигурации список сайтов для индексации?

    private  String textPath;
    private HashSet<String> list = new HashSet<>();

    public IndexingSite(String textPath) {
        this.textPath = textPath;
        this.list = this.parseFile(textPath);
    }

    public String getTextPath() {
        return textPath;
    }

    public HashSet<String> getList() {
        return list;
    }

    private HashSet<String> parseFile(String textPath) {
        try {
            Document doc = Jsoup.connect(textPath).get();

//            System.out.println("name: " + doc.title());
//            System.out.println("url: " + doc.baseUri());

//            SiteTable table = new SiteTable();
//            table.setName(doc.title());
//            table.setUrl(doc.baseUri());
//            table.setStatus(StatusList.INDEXING);
//            table.setStatusTime(new Date());
//            siteRepository.save(table);

            Elements result = doc.select("a[href]");
            for ( Element item : result ) {
                String attrValue = item.attr("href");
                String url = item.absUrl("href");
                if ((attrValue.length() > 1)) {
                    char first = attrValue.charAt(0);
                    if (first == '/')
//                            PageTable pageTable=new PageTable(url,200, item.html());
                            list.add(url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String print() {
        String result="/";
        for(String item: list) {
            result=result.concat("\n").concat(item);
        }
        return result;
    }

}
