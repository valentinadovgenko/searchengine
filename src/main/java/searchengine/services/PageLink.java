package searchengine.services;

import java.util.Objects;

public class PageLink  {
    private String url;
//    private String value;
    private String content;
    private int code;
// TODO: Данный временный класс для парсинга сайтов по заданному пути
    public PageLink(String url) {
        this.url = url;
    }
    public PageLink(String url, String content, int code) {
        this.url = url;
        this.content = content;
        this.code = code;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageLink pageLink)) return false;
        return url.equals(pageLink.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}

