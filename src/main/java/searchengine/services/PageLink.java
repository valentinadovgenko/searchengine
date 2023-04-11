package searchengine.services;

public class PageLink {
    private String url;
    private String value;
    private String content;
    private int code;
// TODO: Данный временный класс для парсинга сайтов по заданному пути
    public PageLink(String url, String value, String content, int code) {
        this.url = url;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

