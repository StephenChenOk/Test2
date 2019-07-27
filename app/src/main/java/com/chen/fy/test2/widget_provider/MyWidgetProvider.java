package com.chen.fy.test2.widget_provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.chen.fy.test2.R;

/**
 * 桌面小部件，实际是一个广播
 */
public class MyWidgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "com.chen.action_CLICK";
    public static final String TAG = "chenyisheng";

    public MyWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        Toast.makeText(context,"收到",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive : action = " + intent.getAction());
        if (intent.getAction().equals(CLICK_ACTION)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.iv_logo, rotateBitmap(context, bitmap, degree));
                        Intent intent1 = new Intent();
                        intent.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
                        remoteViews.setOnClickPendingIntent(R.id.iv_logo, pendingIntent);
                        manager.updateAppWidget(new ComponentName(context, MyWidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();

        }
    }

    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();
        final int counter = appWidgetIds.length;
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intent = new Intent();
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_logo, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
