package com.chen.fy.test2.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.chen.fy.test2.R;

/**
 * android 8.0 后notification需要进行权限操作
 */
public class NotificationUtils extends ContextWrapper {


    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    private Context mContext;

    public NotificationUtils(Context context){
        super(context);
        mContext = context;
    }

    /**
     * 给NotificationManager创建一个Channel
     */
    public void createNotificationChannel(){
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    /**
     * 获取NotificationManager
     */
    private NotificationManager getNotificationManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /**
     * 创建一个RemoteViews，它可以在其它进程中显示
     */
    private RemoteViews getRemoteViews(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.test_1);
        remoteViews.setTextViewText(R.id.test_tv,"chen");
        remoteViews.setImageViewResource(R.id.logo,R.drawable.address);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,
                new Intent(this,Test2Activity.class),0);
        remoteViews.setOnClickPendingIntent(R.id.logo,pendingIntent2); //给id为logo的控件设置点击事件
        return remoteViews;
    }

    /**
     * SDK >= 26时获取Notification
     */
    public Notification.Builder getChannelNotification(String title, String content){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(mContext,Test1Activity.class);
            return new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon))
                    .setAutoCancel(true)
                    .setCustomContentView(getRemoteViews())
                    .setContentIntent(PendingIntent.getActivity(mContext,0,intent,0));
        }
        return null;
    }
    /**
     * SDK < 26时时获取Notification
     */
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    /**
     * 开启通知
     * @param title   标题
     * @param content 内容
     */
    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            //此时NotificationManager已经创建好了Channel
            getNotificationManager().notify(1,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            getNotificationManager().notify(1,notification);
        }
    }
}
