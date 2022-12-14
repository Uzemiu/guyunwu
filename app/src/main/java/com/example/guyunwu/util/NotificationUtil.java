package com.example.guyunwu.util;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import androidx.annotation.DrawableRes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class NotificationUtil {
    public static class NotifyObject implements Serializable {
        public Integer type;
        public String title;
        public String subText;
        public String content;
        public String param;
        public Long firstTime;
        public Class<? extends Activity> activityClass;
        @DrawableRes
        public int icon;
        public List<Long> times = new ArrayList<>();

        public static byte[] toBytes(NotifyObject obj) throws IOException {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(bout);
            oos.writeObject(obj);
            oos.close();
            byte[] bytes = bout.toByteArray();
            bout.close();
            return bytes;
        }

        public static NotifyObject from(String content) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bin = new ByteArrayInputStream(content.getBytes(StandardCharsets.ISO_8859_1));
            ObjectInputStream ois;
            NotifyObject obj;

            ois = new ObjectInputStream(bin);
            obj = (NotifyObject) ois.readObject();
            ois.close();
            bin.close();
            return obj;
        }

        public static String to(NotifyObject obj) throws IOException {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            String content;
            oos = new ObjectOutputStream(bout);
            oos.writeObject(obj);
            oos.close();
            content = bout.toString("ISO-8859-1");
            bout.close();
            return content;
        }
    }

    private static final String TAG = "NotificationUtil";

    /**
     * ??????????????????????????????
     *
     * @param context
     * @param notifyObjectMap
     */
    public static void notifyByAlarm(Context context, Map<Integer, NotifyObject> notifyObjectMap) {
        //?????????????????????
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
     * ????????????
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

        //??????intent
        Intent intent = new Intent(context, obj.activityClass);
        if (obj.param != null && obj.param.trim().length() > 0) {
            intent.putExtra("param", obj.param);
        }

        //notification
        Notification notification;
        // ?????? PendingIntent
        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //????????????

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
     * ?????????????????? ????????????????????????
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
            //????????????
            mPreferences.edit().remove("KEY_MAX_ALARM_ID").apply();

        } catch (Exception e) {
            Log.e(TAG, "??????????????????", e);
        }
    }
}
