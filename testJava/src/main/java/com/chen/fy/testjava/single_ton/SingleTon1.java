package com.chen.fy.testjava.single_ton;

/**
 * 单例模式
 * 1 懒汉式单例
 */
public class SingleTon1 {

    private static SingleTon1 singleTon = null;

    /**
     * 要避免线程安全，需要加上同步
     * 1.方法体同步
     * 但是每一次调用都要用到同步，同步代价相对较大
     */
    public synchronized static SingleTon1 getInstance_1() {
        if (singleTon == null) {
            singleTon = new SingleTon1();
        }
        return singleTon;
    }

    /**
     * 2.代码块同步，双重检查
     * 使需要同步的代码只执行一次
     * 但是也有可能出现无序写入的情况，
     * 比如当构造函数进行到一半时突然跳到另一个线程，就会返回一个未初始化完整的对象
     */
    public static SingleTon1 getInstance_2() {
        if (singleTon == null) {
            synchronized (SingleTon1.class) {
                if (singleTon == null) {
                    singleTon = new SingleTon1();
                }
            }
        }
        return singleTon;
    }

    /**
     * 静态内部类
     * 利用了classloader的机制来保证初始化instance时只有一个线程，所以也是线程安全的，同时没有性能损耗
     */
    private static class LazyHolder{
        static final SingleTon1 SINGLETON_INSTANCE = new SingleTon1();
    }
    public static SingleTon1 getInstance_3(){
        return LazyHolder.SINGLETON_INSTANCE;
    }
}
