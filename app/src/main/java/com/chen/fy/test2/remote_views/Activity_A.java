package com.chen.fy.test2.remote_views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.chen.fy.test2.R;

/**
 * 从进程A中发一个广播送到进程B，实现跨进程的显示RemoteViews
 */
public class Activity_A extends AppCompatActivity {

    private RemoteViews remoteViews;
    private ImageView img;

    private AnimatorSet set;
    private float startX;
    private float startY;
    private float endX = 1000;
    private float endY = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_view_a);
        //remoteViews = getRemoteViews();

        img = findViewById(R.id.img);
        set = new AnimatorSet();
        initAnimation(set, startX, startY, endX, endY);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startX = img.getX();
                startY = img.getY();
                endX = startX + 300;
                endY = startY + 300;
                Toast.makeText(Activity_A.this, "button..", Toast.LENGTH_SHORT).show();
                initAnimation(set, startX, startY, endX, endY);
            }
        });
    }

    private void initAnimation(AnimatorSet set, float startX, float startY, float endX, float endY) {
        Log.d("chenyisheng", startX + "," + startY);
        set.cancel();
        set.playTogether(
                ObjectAnimator.ofFloat(img, "translationX", startX, endX),
                ObjectAnimator.ofFloat(img, "translationY", startY, endY)
        );
        if ((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 200) {
            set.setDuration(1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 400){
            set.setDuration(2 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 600){
            set.setDuration(3 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 800){
            set.setDuration(4 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 1000){
            set.setDuration(5 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 1200){
            set.setDuration(6 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 1400){
            set.setDuration(7 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 1600){
            set.setDuration(8 * 1000).start();
        }else if((Math.abs((startX - endX)) + Math.abs((startY - endY))) < 1800){
            set.setDuration(9 * 1000).start();
        }else {
            set.setDuration(10 * 1000).start();
        }
    }

    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.test_1);
        remoteViews.setTextViewText(R.id.test_tv, "msg from process:" + Process.myPid());
        // remoteViews.setImageViewResource(R.id.logo, R.drawable.address);
        PendingIntent pendingIntent = PendingIntent.getActivity(Activity_A.this, 0
                , new Intent(Activity_A.this, Activity_B.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.logo, pendingIntent);
        return remoteViews;
    }
}
