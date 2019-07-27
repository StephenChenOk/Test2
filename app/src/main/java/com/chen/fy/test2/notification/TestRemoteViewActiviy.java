package com.chen.fy.test2.notification;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chen.fy.test2.R;

public class TestRemoteViewActiviy extends AppCompatActivity implements View.OnClickListener{

    private NotificationManager mManager;
    private Button sendn;
    public static final String id = "channel_1";
    public static final String name = "名字";
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendn = findViewById(R.id.send_notice);
        sendn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notice:
                NotificationUtils notificationUtils = new NotificationUtils(this);
                notificationUtils.sendNotification("测试标题", "测试内容");
        }
    }

}
