package com.example.guyunwu.ui.user.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.guyunwu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initActionBar();
        findViewById(R.id.layout_birthday).setOnClickListener((v) -> {
            Calendar startTime = Calendar.getInstance();
            startTime.set(1970, 0, 1);
            Calendar endTime = Calendar.getInstance();
            TimePickerView pvTime = new TimePickerBuilder(ProfileActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DATE);
                    Toast.makeText(ProfileActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                }
            }).isAlphaGradient(true)
                    .setRangDate(startTime, endTime)
                    .setDate(endTime)
                    .build();
            pvTime.show();
        });
        findViewById(R.id.layout_gender).setOnClickListener((v) -> {
            List<String> gender = new ArrayList<>();
            gender.add("男");
            gender.add("女");
            gender.add("保密");
            List<String> dummy = new ArrayList<>();
            dummy.add(" ");
            OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(ProfileActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    Toast.makeText(ProfileActivity.this, gender.get(option2), Toast.LENGTH_SHORT).show();
                }
            }).isAlphaGradient(true)
                    .build();
            pvOptions.setNPicker(dummy, gender, dummy);
            pvOptions.show();
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
            bar.setTitle("个人资料");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

