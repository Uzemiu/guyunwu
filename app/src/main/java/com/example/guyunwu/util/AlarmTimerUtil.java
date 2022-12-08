package com.example.guyunwu.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.Map;

public class AlarmTimerUtil {

    public static void setAlarmTimer(Context context, int alarmId, long time, String action, Map<String, Serializable> map) {
        Intent myIntent = new Intent();
        myIntent.setAction(action);
        if (map != null) {
            for (String key : map.keySet()) {
                myIntent.putExtra(key, map.get(key));
            }
        }
        PendingIntent sender = PendingIntent.getService(context, alarmId, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, sender);
    }

    public static void cancelAlarmTimer(Context context, String action, int alarmId) {
        Intent myIntent = new Intent();
        myIntent.setAction(action);
        PendingIntent sender = PendingIntent.getService(context, alarmId, myIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(sender);
    }
}
