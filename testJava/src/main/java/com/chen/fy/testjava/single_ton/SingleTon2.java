package com.chen.fy.testjava.single_ton;

/**
 * 单例模式
 * 1 饿汉式单例
 */
public class SingleTon2 {

    //一开始直接创建好对象，不再改变，所以是线程安全
    private static SingleTon2 singleTon2 = new SingleTon2();

    public static SingleTon2 getInstance(){
        return singleTon2;
    }

}
