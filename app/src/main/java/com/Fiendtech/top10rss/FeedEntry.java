package com.Fiendtech.top10rss;

public class FeedEntry {
    private String title;
    private String name;
    private String category;
    private String imgUrl;
    private String releasedate;

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return
                "title=" + title + '\n' +
                ", name=" + name + '\n' +
                ", category=" + category + '\n' +
                ", im:image=" + imgUrl + '\n' +
                ", releasedate=" + releasedate + '\n';
    }
}
