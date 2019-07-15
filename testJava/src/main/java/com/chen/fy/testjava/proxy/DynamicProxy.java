package com.chen.fy.testjava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理接口
 */
public class DynamicProxy implements InvocationHandler {

    //真实对象，即需要被代理的对象
    private Object real;

    public Object getProxy(Object real) {
        this.real = real;
        //通过真实对象构建一个代理
        return Proxy.newProxyInstance(
                real.getClass().getClassLoader()
                , real.getClass().getInterfaces(),
                this);
    }

    /**
     * @param o       被代理的对象
     * @param method  要操作的放发
     * @param objects 方法要传入的参数，可以没有，也可以为Null
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //通过反射调用真实对象的方法
        return method.invoke(real, objects);
    }
}
