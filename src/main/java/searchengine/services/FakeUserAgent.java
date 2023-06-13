package searchengine.services;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeUserAgent {
    List<String> userList = new ArrayList<>();

    public FakeUserAgent() {
        this.userList = createListUserAgent();
    }


    public List<String> createListUserAgent() {
        userList.add("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
        userList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        userList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        userList.add("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 YaBrowser/20.12.2.105 Yowser/2.5 Safari/537.36");
        userList.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36 Maxthon/5.3.8.2000");
        userList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 11.1; rv:84.0) Gecko/20100101 Firefox/84.0");
        return userList;
    }


    public String enterRandomUserAgent() {
        return userList.get((int) (Math.random()* userList.size()));
    }
}
