package com.example.guyunwu.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class NotificationUtil {

    private static final String TAG = "NotificationUtil";

    /**
     * 通过定时闹钟发送通知
     *
     * @param context
     * @param notifyObjectMap
     */
    public static void notifyByAlarm(Context context, Map<Integer, NotifyObject> notifyObjectMap) {
        //将数据存储起来
        int count = 0;

        Set<Integer> keySet = notifyObjectMap.keySet();
        for (Integer key0 : keySet) {
            if (!notifyObjectMap.containsKey(key0)) {
                break;
            }

            NotifyObject obj = notifyObjectMap.get(key0);
            if (obj == null) {
                break;
            }

            if (obj.times.size() == 0) {
                if (obj.firstTime > 0) {
                    try {
                        Map<String, Serializable> map = new HashMap<>();
                        map.put("KEY_NOTIFY_ID", obj.type);
                        map.put("KEY_NOTIFY", NotifyObject.to(obj));
                        AlarmTimerUtil.setAlarmTimer(context, ++count, obj.firstTime, "TIMER_ACTION", map);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (long time : obj.times) {
                    if (time > 0) {
                        try {
                            Map<String, Serializable> map = new HashMap<>();
                            map.put("KEY_NOTIFY_ID", obj.type);
                            map.put("KEY_NOTIFY", NotifyObject.to(obj));
                            AlarmTimerUtil.setAlarmTimer(context, ++count, time, "TIMER_ACTION", map);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        SharedPreferences mPreferences = context.getSharedPreferences("SHARE_PREFERENCE_NOTIFICATION", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putInt("KEY_MAX_ALARM_ID", count);
        edit.apply();
    }

    public static void notifyByAlarmByReceiver(Context context, NotifyObject obj) {
        if (context == null || obj == null) return;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMsg(context, obj, obj.type, System.currentTimeMillis(), manager);
    }

    /**
     * 消息通知
     *
     * @param context
     * @param obj
     */
    private static void notifyMsg(Context context, NotifyObject obj, int nid, long time, NotificationManager mNotifyMgr) {
        if (context == null || obj == null) return;
        if (mNotifyMgr == null) {
            mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (time <= 0) return;

        //准备intent
        Intent intent = new Intent(context, obj.activityClass);
        if (obj.param != null && obj.param.trim().length() > 0) {
            intent.putExtra("param", obj.param);
        }

        //notification
        Notification notification;
        // 构建 PendingIntent
        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容

        String id = "my_channel_01";
        int importance = NotificationManager.IMPORTANCE_LOW;
        CharSequence name = "notice";
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.enableLights(true);
        mChannel.setDescription("just show notice");
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.GREEN);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotifyMgr.createNotificationChannel(mChannel);

        Notification.Builder builder = new Notification.Builder(context, id);
        builder.setAutoCancel(true)
                .setContentIntent(pi)
                .setContentTitle(obj.title)
                .setContentText(obj.content)
                .setOngoing(false)
                .setSmallIcon(obj.icon)
                .setWhen(System.currentTimeMillis());
        if (obj.subText != null && obj.subText.trim().length() > 0) {
            builder.setSubText(obj.subText);
        }
        notification = builder.build();
        if (notification != null) {
            mNotifyMgr.notify(nid, notification);
        }
    }

    /**
     * 取消所有通知 同时取消定时闹钟
     *
     * @param context
     */
    public static void clearAllNotifyMsg(Context context) {
        try {

            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.cancelAll();

            SharedPreferences mPreferences = context.getSharedPreferences("SHARE_PREFERENCE_NOTIFICATION", Context.MODE_PRIVATE);
            int max_id = mPreferences.getInt("KEY_MAX_ALARM_ID", 0);
            for (int i = 1; i <= max_id; i++) {
                AlarmTimerUtil.cancelAlarmTimer(context, "TIMER_ACTION", i);
            }
            //清除数据
            mPreferences.edit().remove("KEY_MAX_ALARM_ID").apply();

        } catch (Exception e) {
            Log.e(TAG, "取消通知失败", e);
        }
    }
}
