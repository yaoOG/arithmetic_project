package com.yao.designPattern.singletonPattern;

/**
 * Created by zhuyao on 2018/05/04.
 */
public class Singleton {

    private volatile static Singleton instance;
    private Singleton(){
    }

    public static Singleton getSingleton(){
        if (instance == null){
            synchronized (Singleton.class){
                if (instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
