package com.example.android.apis.mydemo.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Student {
    // 主键必须使用包装数据类型
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int gender;


    @Generated(hash = 1509742286)
    public Student(Long id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }


    @Generated(hash = 1556870573)
    public Student() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getGender() {
        return this.gender;
    }


    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
