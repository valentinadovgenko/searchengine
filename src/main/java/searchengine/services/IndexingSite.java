package searchengine.services;

import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class IndexingSite {
// TODO: Запуск сервиса индефикации сайтов.

    // TODO: Брать из конфигурации приложения список сайтов config/Site
    private String textPath;
    //Должен быть потокобезопасный HashTable
//    private Hashtable<String,String> mapPage = new Hashtable<>();
    private LinkedHashSet<PageLink> vector = new LinkedHashSet<>();
    static int exceptionCode = 0;
    public IndexingSite(String textPath) {
        this.textPath = textPath;
        vector=parseSite(textPath);
    }

    public String getTextPath() {
        return textPath;
    }

    public LinkedHashSet<PageLink> getVector() {
        return vector;
    }


//    public Hashtable<String, String> getMapPage() {
//        return mapPage;
//    }

    private LinkedHashSet<PageLink> parseSite(String textPath)  {

        try {
            Document doc = Jsoup.connect(textPath).get();
//            System.out.println(doc.outerHtml());
            Elements result = doc.select("a[href]");
            for ( Element item : result ) {
                String value = item.attr("href");
                String url = item.absUrl("href");
                if ((value.length() > 1)) {
                    char first = value.charAt(0);
                    String per="";
                    if (first == '/') {
                        per = parseContent(url);
                        if (notFound(vector, url)) {
                            vector.add(new PageLink(url, value, per, exceptionCode));
                            System.out.println(value + " / " + exceptionCode);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vector;
    }

    public static String parseContent(String url) throws IOException {
        URL urlConnet = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlConnet.openConnection();
        exceptionCode = http.getResponseCode();
        try {
            Document document=Jsoup.connect(url).get();
            return document.outerHtml();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "NO";
        }
    }

// Проверка на неповторимость ссылки
    public Boolean notFound(LinkedHashSet<PageLink> list, String url) {
        boolean result = true;
        for ( PageLink p : list ) {
            if (p.getUrl().equals(url)) {
                result = false;
                break;
            }
        }
        return result;
    }

}
