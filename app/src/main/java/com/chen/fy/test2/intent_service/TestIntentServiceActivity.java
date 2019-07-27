package com.chen.fy.test2.intent_service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chen.fy.test2.R;

/**
 * 测试HandlerThread以及IntentService
 */
public class TestIntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_service);

        //testHandlerThread();
        testIntentService();
    }

    /**
     * 测试HandlerThread
     * 在thread中存在一个handler,这使得线程之间有了进行交流的可能，也可以随时
     * 对线程进行停止
     */
    private void testHandlerThread() {

        final int i = 0;
        //1 创建HandlerThread对象                              线程名
        final HandlerThread handlerThread = new HandlerThread("ThreadName"){
            @Override
            public void run() {
                super.run();   //不可以去掉，因为在其方法中会进行创建loop等的操作
                while (true){
                    Log.d("runrun:", String.valueOf(i));
                }
            }
        };
        //2 开启线程，同时开启消息循环
        handlerThread.start();
        //3 通过消息循环初始化Handler,
        Handler handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {  //在handlerThread线程
                super.handleMessage(msg);
                Log.d("handleMessage:", "handleMessage");
                handlerThread.quit();   //只会停止消息队列的循环loop，而不会停止线程的运行
            }
        };
        //4 通过handler发送消息，此时消息会被发送到handlerThread线程中的消息队列中
        handler.sendEmptyMessageDelayed(0,2000);
        handler.sendEmptyMessageDelayed(0,2000);
    }

    private void testIntentService(){
        Intent intent = new Intent(this,MyIntentService.class);
        intent.putExtra("test","1");
        startService(intent);
        startService(intent);
        startService(intent);
    }
}
