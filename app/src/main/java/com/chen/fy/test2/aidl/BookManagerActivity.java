package com.chen.fy.test2.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chen.fy.test2.IBookManager;
import com.chen.fy.test2.IOnNewBookArrivedListener;
import com.chen.fy.test2.MyBook;
import com.chen.fy.test2.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BookManagerActivity extends AppCompatActivity {


    private static final String TAG = "chenyisheng";

    //新书到达标识符
    private static final int NEW_BOOK_ARRIVED = 1;

    private IBookManager mIBookManager;

    /**
     * 为了防止使用handler时内存泄漏，可以采用弱引用的方法避免
     * 因为如果不这样做，当此Activity结束后，但是handler仍然持有Activity的引用，这就会造成GC无法对Activity进行回收
     * --》app启动后，系统就会创建一个Looper对象，并实现一个消息队列（Message Queue），然后开启一个循环来处理Message对象
     * --》当handler发送消息时，消息就会进入消息队列中，而要想在handler中实现handleMessage（）方法，则消息必须持有handler的引用
     * --》又在java中，非静态的内部类或者匿名类持有外部类的引用（静态的不持有），即handler又持有了外部activity的引用
     * --》引用链：message – handler – activity
     */
    private Handler mHandler = new MyHandler(this); //传入当前activity的引用
    /**
     * 声明一个静态的handler，并持有外部的弱引用
     * 强引用：GC绝不收回，即使内存溢出抛出异常
     * 软引用：GC能不收回就不收回（指内存足够的情况）
     * 弱引用：GC发现就收回，但是一般情况下不会发现。
     */
    private static class MyHandler extends Handler {
        //获取外部的弱引用
        private WeakReference<BookManagerActivity> mActivity;

        MyHandler(BookManagerActivity bookManagerActivity) {
            mActivity = new WeakReference<>(bookManagerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            //当外部activity已经被回收或者正在被回收时，结束
            if (mActivity.get() == null || mActivity.get().isFinishing()) {
                return;
            }
            switch (msg.what) {   //有新书进入
                case NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive a new book...");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //新书到达的接口
    private IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(MyBook newBook) throws RemoteException {
            //从 message pool中返回一个message，避免过多的资源消耗
            mHandler.obtainMessage(NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBookManager = IBookManager.Stub.asInterface(service);
            ArrayList<MyBook> list = null;
            try {
                list = (ArrayList<MyBook>) mIBookManager.getBooks();
                Log.d(TAG, list.toString());
                mIBookManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBookManager = null;
            Log.d(TAG, "binder is died");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);  //默认创建binder
    }

    @Override
    protected void onDestroy() {
        if(mIBookManager != null && mIBookManager.asBinder().isBinderAlive()){
            try {
                mIBookManager.unregisterListener(mListener);  //解除监听
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }

}
