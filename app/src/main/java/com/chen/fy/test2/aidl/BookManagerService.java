package com.chen.fy.test2.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chen.fy.test2.IBookManager;
import com.chen.fy.test2.IOnNewBookArrivedListener;
import com.chen.fy.test2.MyBook;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 服务端
 */
public class BookManagerService extends Service {

    private static final String TAG = "chenyisheng";

    //以原子的形式更新boolean的值，也就是说这个值在多线程同步中是安全的
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    // 自动支持并发读/写
    // aidl中的方法是在binder线程池中执行的，所以就会存在多个线程同时访问
    private CopyOnWriteArrayList<MyBook> bookList = new CopyOnWriteArrayList<MyBook>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> listeners =
            new CopyOnWriteArrayList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() { //创建aidl文件时自动生成的代码
        @Override
        public List<MyBook> getBooks() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(MyBook book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            //注册监听器
            if(!listeners.contains(listener)){
                listeners.add(listener);
                Log.d(TAG,"监听器注册成功");
            }else{
                Log.d(TAG,"监听器已经存在！");
            }
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            //取消监听器
            if(listeners.contains(listener)){
                listeners.remove(listener);
                Log.d(TAG,"监听器取消成功....");
            }else{
                Log.d(TAG,"监听器不存在，无法取消...");
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        bookList.add(new MyBook("1",1));
        bookList.add(new MyBook("2",2));
        bookList.add(new MyBook("3",3));

        //开启子线程，实现每隔3s有一本新书到达
        new Thread(new ServiceWorker()).start();
    }

    //当有新书到来时
    private void onNewBookArrived(MyBook myBook) throws RemoteException {
        bookList.add(myBook);
        for(int i=0;i<listeners.size();i++){
            IOnNewBookArrivedListener listener = listeners.get(i);
            listener.onNewBookArrived(myBook);
        }
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyBook myBook = new MyBook((System.currentTimeMillis()%10)+"",
                        (int) (System.currentTimeMillis()%10));
                try {
                    onNewBookArrived(myBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
