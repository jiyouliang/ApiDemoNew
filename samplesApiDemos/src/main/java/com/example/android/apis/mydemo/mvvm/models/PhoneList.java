package com.example.android.apis.mydemo.mvvm.models;

import java.util.List;

public class PhoneList {

    private int code;
    private String message;
    List<Phone> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Phone> getData() {
        return data;
    }

    public void setData(List<Phone> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PhoneList{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
