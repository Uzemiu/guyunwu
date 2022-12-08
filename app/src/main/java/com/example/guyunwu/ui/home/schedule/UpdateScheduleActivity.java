package com.example.guyunwu.ui.home.schedule;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.guyunwu.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class UpdateScheduleActivity extends AppCompatActivity {
    
    public Integer word_per_day = 500;

    public Integer words = 4135;

    private List<Integer> days;

    public static Integer[] values = new Integer[]{
            5, 10, 15, 20, 25, 25, 30, 35, 40, 45,
            50, 55, 60, 65, 70, 75, 80, 85, 90, 95,
            100, 125, 150, 175, 200, 225, 250, 275, 300,
            325, 350, 375, 400, 425, 450, 475, 500, 550,
            600, 650, 700, 750, 800, 850, 900, 950, 1000
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);
        initActionBar();

        initSelectNumWheel();
        initSelectDayWheel();
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
            bar.setTitle("学习日历");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    private void initSelectNumWheel() {
        WheelView select_num_view = findViewById(R.id.select_num);
        select_num_view.setCyclic(false);

        final List<String> mOptionsItems = new ArrayList<>();
        for(Integer num : values) {
            mOptionsItems.add(num.toString() + "个");
        }
        select_num_view.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        select_num_view.setCurrentItem(getCurrentNumItem());

        select_num_view.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                word_per_day = values[index];
                updateSelectDayWheel();
                Toast.makeText(UpdateScheduleActivity.this,  mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSelectDayWheel() {
        WheelView select_day_view = findViewById(R.id.select_day);
        select_day_view.setCyclic(false);

        final List<String> mOptionsItems = new ArrayList<>();
        days = getDayList();
        for(Integer num : days) {
            mOptionsItems.add(num.toString() + "天");
        }
        select_day_view.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        select_day_view.setCurrentItem(getCurrentDayItem(days));

        select_day_view.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                updateSelectNumWheel(index);
                Toast.makeText(UpdateScheduleActivity.this,  mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSelectDayWheel() {
        WheelView select_day_view = findViewById(R.id.select_day);
        select_day_view.setCurrentItem(getCurrentDayItem(days));
    }

    private void updateSelectNumWheel(int index) {
        WheelView select_num_view = findViewById(R.id.select_num);

        int day_planned = days.get(index);
        int least_words_per_day = (int)Math.ceil((double) words / day_planned);
        for(int i = 0; i < values.length; i++){
            if(values[i] >= least_words_per_day) {
                select_num_view.setCurrentItem(i);
                break;
            }
        }
    }

    private int getCurrentDayItem(List<Integer> days) {
        for(int index = 0; index < days.size(); index++) {
            if(days.get(index) == (int)Math.ceil((double) words / word_per_day)) {
                return index;
            }
        }
        return 0;
    }

    private int getCurrentNumItem() {
        for(int index = 0; index < values.length; index++) {
            if(Objects.equals(word_per_day, values[index])) {
                return index;
            }
        }
        return 0;
    }

    private List<Integer> getDayList() {
        LinkedHashSet<Integer> daySet = new LinkedHashSet<>();

        for(int index = values.length - 1; index >= 0; index--) {
            int day = (int)Math.ceil((double) words / values[index]);
            daySet.add(day);
        }

        return new ArrayList<>(daySet);
    }

}
