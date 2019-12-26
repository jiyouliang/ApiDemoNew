package com.example.android.apis.mydemo.web;

public class SmoothModel {
    private String content;
    private String url;
    private int type;

    public SmoothModel(String content) {
        this.content = content;
    }

    public SmoothModel(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SmoothModel(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SmoothModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
