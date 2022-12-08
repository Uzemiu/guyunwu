package com.example.guyunwu.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.guyunwu.util.NotificationUtil;

import java.io.IOException;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String str = intent.getStringExtra("KEY_NOTIFY");
        if (str == null || str.trim().length() == 0) return super.onStartCommand(intent, flags, startId);
        try {
            NotificationUtil.NotifyObject obj = NotificationUtil.NotifyObject.from(str);
            NotificationUtil.notifyByAlarmByReceiver(this, obj);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
