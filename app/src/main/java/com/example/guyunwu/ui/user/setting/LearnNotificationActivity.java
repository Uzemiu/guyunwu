package com.example.guyunwu.ui.user.setting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.guyunwu.R;

import java.util.Calendar;
import java.util.Date;

public class LearnNotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_notification);
        initActionBar();
        findViewById(R.id.layout_notification_time).setOnClickListener(v -> {
            TimePickerView pvTime = new TimePickerBuilder(LearnNotificationActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    Toast.makeText(LearnNotificationActivity.this, hour + ":" + minute, Toast.LENGTH_SHORT).show();
                }
            }).setType(new boolean[]{false, false, false, true, true, false})
                    .isAlphaGradient(true)
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
}
