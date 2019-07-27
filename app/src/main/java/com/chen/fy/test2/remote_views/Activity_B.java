package com.chen.fy.test2.remote_views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.chen.fy.test2.R;

/**
 * 从进程A中发一个广播送到进程B，实现跨进程的显示RemoteViews
 */
public class Activity_B extends AppCompatActivity {

    private LinearLayout mRemoteViewLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_view_b);

        initView();
    }

    private void initView() {
        mRemoteViewLayout = findViewById(R.id.box_B);
        IntentFilter intentFilter = new IntentFilter("com.chen.ACTION_TO_B");
        registerReceiver(receiver,intentFilter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("chenyisheng","receiver...");
            RemoteViews remoteViews = intent.getParcelableExtra("remoteViews");
            if(remoteViews!=null){
                updateUI(remoteViews);
            }
        }
    };

    private void updateUI(RemoteViews remoteViews) {
        View view = remoteViews.apply(this,mRemoteViewLayout);
        if(mRemoteViewLayout==null){
            Log.d("chenyisheng","null...");
        }
        mRemoteViewLayout.addView(view);
//        int layoutId = getResources().getIdentifier("test_1","layout",getPackageName());
//        View view = getLayoutInflater().inflate(layoutId,mRemoteViewLayout,false);
//        remoteViews.reapply(this,view);
//        mRemoteViewLayout.addView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
