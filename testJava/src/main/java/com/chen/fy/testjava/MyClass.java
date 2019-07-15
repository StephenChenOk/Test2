package com.chen.fy.testjava;

import com.chen.fy.testjava.proxy.DynamicProxy;
import com.chen.fy.testjava.proxy.ProxySubject;
import com.chen.fy.testjava.proxy.RealSubject;
import com.chen.fy.testjava.proxy.Subject;
import com.chen.fy.testjava.single_ton.SingleTon1;

/**
 * 测试Java中的各种设计模式
 */
public class MyClass {

    public static void main(String[] arg){
        dynamicProxyModel();
    }

    /**
     * 动态代理模式
     */
    private static void dynamicProxyModel() {
        //获取真实对象接口
        Subject realSubject = new RealSubject();

        // 取得动态代理,返回的必须是接口，不可以是实现类，
        // 因为代理类已经默认继承了Proxy类，则必须使用接口才能使用被代理类的方法
        // 动态代理后生成的代理类如下：
        // public final class mProxy extends Proxy implements YourInterface {}
        Subject real = (Subject) new DynamicProxy().getProxy(realSubject);
        real.doSomething();
    }

    /**
     * 静态代理模式
     * >避免直接访问委托类
     */
    private static void staticProxyModel() {
        RealSubject realSubject = new RealSubject();
        ProxySubject proxySubject = new ProxySubject(realSubject);
        proxySubject.doSomething();
        proxySubject.doOtherthing();
    }

}
