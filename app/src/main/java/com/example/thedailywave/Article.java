package com.example.thedailywave;

public class Article {
    private String title;
    private String urlToImage;
    private String url;
    private Source source;

    public String getTitle() { return title; }
    public String getUrlToImage() { return urlToImage; }
    public String getUrl() { return url; }
    public Source getSource() { return source; }

    public static class Source {
        private String name;
        public String getName() { return name; }
    }

}
