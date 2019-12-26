package com.example.android.apis.mydemo.web;

/**
 * 评论用户
 */
public class CommentUserModel {
    private String userName;
    private String comment;

    public CommentUserModel() {
    }

    public CommentUserModel(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentUserModel{" +
                "userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
