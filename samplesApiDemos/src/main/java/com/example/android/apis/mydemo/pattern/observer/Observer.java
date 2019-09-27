package com.example.android.apis.mydemo.pattern.observer;

/**
 * 观察者抽象接口
 */
public interface Observer {
    /**
     * 通知观察者
     * @param msg
     */
    void notify(String msg);
}
