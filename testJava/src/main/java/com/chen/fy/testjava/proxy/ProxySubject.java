package com.chen.fy.testjava.proxy;

import sun.rmi.runtime.Log;

/**
 * 代理类
 */
public class ProxySubject implements Subject {

    private Subject real;

    public ProxySubject(Subject subject){
        this.real = subject;
    }

    @Override
    public void doSomething() {
        System.out.println("ProxySubject before real doSomething");
        real.doSomething();
        System.out.println("ProxySubject after real doSomething");
    }

    public void doOtherthing(){
        System.out.println("ProxySubject doOtherthing");
    }
}
