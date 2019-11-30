package com.tpj.teamproject.ui.mypage;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.tpj.teamproject.R;
import com.tpj.teamproject.ui.MainActivity;

public class PushAlarmManager {
    public PushAlarmManager(final MyPageFragment fragment, final long time){
        final Activity activity = fragment.getActivity();
        //푸시 알림을 보내기위해 시스템에 권한을 요청하여 생성
        final NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);

        //푸시 알림 터치시 실행할 작업 설정(여기선 MainActivity로 이동하도록 설정)

        final Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        //Notification 객체 생성
        final Notification.Builder builder = new Notification.Builder(activity.getApplicationContext());

        //앞서 생성한 작업 내용을 Notification 객체에 담기 위한 PendingIntent 객체 생성
        PendingIntent pendnoti = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //푸시 알림에 대한 각종 설정

        builder.setSmallIcon(R.drawable.ic_launcher_background).setTicker("Ticker").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("Content Title").setContentText("Content Text").setWhen(time)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendnoti).setAutoCancel(true).setOngoing(true);

        //NotificationManager를 이용하여 푸시 알림 보내기
        notificationManager.notify(1, builder.build());
    }
}
