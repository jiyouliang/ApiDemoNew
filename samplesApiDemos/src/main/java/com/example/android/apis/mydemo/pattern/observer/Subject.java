package com.example.android.apis.mydemo.pattern.observer;

/**
 * 被观察者抽象接口
 */
public interface Subject {
    /**
     * 添加/注册观察者
     * @param o
     */
    void addObserver(Observer o);

    /**
     * 删除/注销观察者
     * @param o
     */
    void removeObserver(Observer o);

    /**
     * 通知所有观察者
     */
    void notifyObservers();
}
