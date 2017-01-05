package com.example.weichenglau.personalDiary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class AlarmService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 抓取報價
        new Thread() {
            @Override
            public void run() {

                // 建立 Notification
                int nid = 100;
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), nid, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                 NotificationManager notificationManager =
                        (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder =
                        new Notification.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_menu_send) // 通知服務 icon
                        .setContentIntent(pendingIntent)
                        .setContentTitle("write daily diary") // 標題
                        .setContentText("你還沒紀錄") // 內文
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);

                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                // 抬頭顯示儀
                builder.setPriority(Notification.PRIORITY_HIGH); // 亦可帶入Notification.PRIORITY_MAX參數
                Notification notification = builder.build();
                notificationManager.notify(nid, notification); // 發佈Notification

            }

        }.start();

        // 關閉服務
        stopSelf();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.i("mylog", "onDestroy()");
        super.onDestroy();
    }
}
