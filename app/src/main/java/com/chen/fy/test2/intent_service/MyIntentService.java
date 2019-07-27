package com.chen.fy.test2.intent_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * IntentService会开启一个线程来完成服务中的任务
 * 任务完成后会自行结束
 */
public class MyIntentService extends IntentService {

    public MyIntentService(String name) {
        super(name);   //命名工作线程
    }

    public MyIntentService(){
        super("myIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String text = intent.getStringExtra("test");
        if(text.equals(1)){
            Log.d("getStringExtra","111");
        }else{
            Log.d("getStringExtra","222");
        }
        Log.d("onHandleIntent",Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            Log.d("onHandleIntent","3333");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onHandleIntent","HandleIntent onDestroy...");
    }
}
