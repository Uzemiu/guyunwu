package com.example.guyunwu.ui.user.setting;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.guyunwu.MainActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.entity.SettingEntity;
import com.example.guyunwu.entity.SettingEnum;
import com.example.guyunwu.repository.SettingRepository;
import com.example.guyunwu.util.NotificationUtil;
import com.example.guyunwu.util.NotifyObject;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LearnNotificationActivity extends AppCompatActivity {

    private SettingEntity hasNotification;

    private SettingEntity notificationTime;

    private final SettingRepository settingRepository = new SettingRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_notification);
        initActionBar();
        loadSettings();
        findViewById(R.id.learn_everyday_switch).setOnClickListener(v -> {
            hasNotification.setBooleanData(!hasNotification.getBooleanData());
            if (!hasNotification.getBooleanData()) {
                NotificationUtil.clearAllNotifyMsg(this);
            }
            settingRepository.update(hasNotification);
        });
        findViewById(R.id.layout_notification_time).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(notificationTime.getDateData());
            TimePickerView pvTime = new TimePickerBuilder(LearnNotificationActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    notificationTime.setDateData(date);
                    settingRepository.update(notificationTime);
                    TextView textView = findViewById(R.id.notification_time);
                    DateFormat format = new SimpleDateFormat("HH:mm");
                    textView.setText(format.format(notificationTime.getDateData()));
                    if (hasNotification.getBooleanData()) {
                        notifyEveryday();
                    }
                }
            }).setType(new boolean[]{false, false, false, true, true, false})
                    .isAlphaGradient(true)
                    .setDate(calendar)
                    .build();
            pvTime.show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //返回键的id
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("学习提醒");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadSettings() {
        hasNotification = settingRepository.findById(SettingEnum.HAS_NOTIFICATION.ordinal());
        notificationTime = settingRepository.findById(SettingEnum.NOTIFICATION_TIME.ordinal());

        SwitchMaterial switchMaterial = findViewById(R.id.learn_everyday_switch);
        switchMaterial.setChecked(hasNotification.getBooleanData());
        TextView textView = findViewById(R.id.notification_time);
        DateFormat format = new SimpleDateFormat("HH:mm");
        textView.setText(format.format(notificationTime.getDateData()));
    }

    public void notifyEveryday() {
        NotificationUtil.clearAllNotifyMsg(this);
        long now = System.currentTimeMillis();
        long time = notificationTime.getDateData().getTime();
        while (time <= now) time += AlarmManager.INTERVAL_DAY;
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
        Toast.makeText(this, "下次提醒将在 \n" + format.format(date), Toast.LENGTH_LONG).show();
        Map<Integer, NotifyObject> notifyObjects = new HashMap<>();

        NotifyObject obj = new NotifyObject();
        obj.type = 1;
        obj.title = "每日提醒";
        obj.subText = "每日提醒";
        obj.content = "快去学习吧！";
        obj.firstTime = time;
        obj.icon = R.mipmap.ic_launcher;
        obj.activityClass = MainActivity.class;
        notifyObjects.put(obj.type, obj);

        NotificationUtil.notifyByAlarm(this, notifyObjects);
    }
}
