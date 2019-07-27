package com.chen.fy.test2.widget_provider;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chen.fy.test2.R;

public class WidgetProviderActivity extends AppCompatActivity {

    IntentFilter intentFilter;
    MyWidgetProvider myWidgetProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_main);

        Button send_btn = findViewById(R.id.send_btn);

        //动态注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.chen.action_CLICK");
        intentFilter.addAction("android.appwidget.action.APPWIDGET_UPDATE");

        myWidgetProvider = new MyWidgetProvider();
        registerReceiver(myWidgetProvider,intentFilter);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.chen.action_CLICK");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myWidgetProvider);
    }
}
