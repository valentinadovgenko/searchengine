package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.config.Task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class IndexingSite extends RecursiveTask<LinkedHashSet> {

    private String url;
    static int exceptionCode = 0;
    static volatile LinkedHashSet<PageLink> finalSetForPage = new LinkedHashSet<>();

    public static void setStopIndexing(boolean stopIndexing) {
        IndexingSite.stopIndexing = stopIndexing;
    }

    public static boolean isStopIndexing() {
        return stopIndexing;
    }

    static volatile boolean stopIndexing=false;
    static volatile String userName = "";

    public IndexingSite(String url) {
        this.url = url;
    }

    @Override
    protected LinkedHashSet<PageLink> compute() {
        List<IndexingSite> taskList = new ArrayList<>();
        LinkedHashSet<PageLink> pageLinks = new LinkedHashSet<>(parserSite(url));
        System.out.println(Thread.currentThread().getName());
        finalSetForPage.addAll(pageLinks);
        for ( PageLink item : pageLinks ) {


            if (!stopIndexing) {
                IndexingSite task = new IndexingSite(item.getUrl());
//            task.fork();
                taskList.add(task);
            } else {
                Thread.interrupted();
                break;}

         }
        invokeAll(taskList);

//        for ( IndexingSite task : taskList ) {
//            pageLinks.addAll(task.join());
//        }
        return finalSetForPage;
    }

    private LinkedHashSet<PageLink> parserSite(String link) {
        LinkedHashSet<PageLink> pageLinks = new LinkedHashSet<>();
        userName = new FakeUserAgent().enterRandomUserAgent();
        try {
            Document doc = Jsoup.connect(link).userAgent(userName).referrer("http://www.google.com").get();
            Elements results = doc.select("a");
            for ( Element item : results ) {
                String path = item.absUrl("href");
                String basePath = item.baseUri();
                if (path.startsWith(basePath) && (!path.isEmpty()) && (!path.endsWith(".pdf"))
                        && (!finalSetForPage.contains(new PageLink(path))) ) {
                        pageLinks.add(new PageLink(path, parserLink(path), exceptionCode));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageLinks;
    }

    // Парсим каждую ссылку отдельно и возвращаем код соединения и содержимое кода
    public static String parserLink(String path) throws IOException {

        URL urlConnet = new URL(path);
        HttpURLConnection http = (HttpURLConnection) urlConnet.openConnection();
        exceptionCode = http.getResponseCode();

        if (exceptionCode <= 299) {
            Document document = Jsoup.connect(path).userAgent(userName).referrer("http://www.google.com").get();
            return document.outerHtml();
        } else {
            return "NO";
        }
    }
    public static void clearFinalSetForPage() {
        finalSetForPage.clear();
    }


}
