package searchengine.model;

import searchengine.config.Site;

import javax.persistence.*;

@Entity
@Table(name = "page", indexes = {
        @Index(name="site_id", columnList = "id")
})
public class PageTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private SiteTable site;
    @Column(name = "path", nullable = false, unique = true)
    private String pathText;
    @Column(nullable = false)
    private int code;
    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    public PageTable() {
    }

    public PageTable(SiteTable site,String pathText, int code, String content) {
        this.site = site;
        this.pathText = pathText;
        this.code = code;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SiteTable getSite() {
        return site;
    }

    public void setSite(SiteTable site) {
        this.site = site;
    }

    public String getPathText() {
        return pathText;
    }

    public void setPathText(String pathText) {
        this.pathText = pathText;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
