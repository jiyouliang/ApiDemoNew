package com.example.android.apis.mydemo.mvvm.models;

public class Phone {

    /**
     * title : 华为（HUAWEI） P30
     * image : http://jiyouliang.com/demo/images/p1.webp
     */

    private String title;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
