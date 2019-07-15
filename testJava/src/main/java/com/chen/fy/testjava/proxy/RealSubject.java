package com.chen.fy.testjava.proxy;

/**
 * 委托类
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("RealSubject do Something");
    }
}
