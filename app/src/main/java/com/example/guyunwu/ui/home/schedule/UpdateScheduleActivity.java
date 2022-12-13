package com.example.guyunwu.ui.home.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.ScheduleRequest;
import com.example.guyunwu.api.req.UpdateScheduleReq;
import com.example.guyunwu.api.resp.ScheduleResp;
import com.example.guyunwu.api.resp.SimpleScheduleResp;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateScheduleActivity extends AppCompatActivity {

    private static final String TAG = "UpdateScheduleActivity";

    private int wordPerDay = SharedPreferencesUtil.getInt("wordsPerDay", 10);

    private int words = 4135;

    private List<Integer> days;

    private int currentWordPerDay = SharedPreferencesUtil.getInt("wordsPerDay", 10);

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
    }

    @Override
    public void onStart() {
        super.onStart();
        initSchedule();
        initBinding();
        initSelectNumWheel();
        initSelectDayWheel();
    }

    private void initSchedule() {
        ScheduleRequest scheduleRequest = RequestModule.SCHEDULE_REQUEST;
        scheduleRequest.getSchedule(SharedPreferencesUtil.getLong("scheduleId", 0)).enqueue(new Callback<BaseResponse<ScheduleResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<ScheduleResp>> call, Response<BaseResponse<ScheduleResp>> response) {
                BaseResponse<ScheduleResp> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("获取计划失败"));
                } else {
                    wordPerDay = SharedPreferencesUtil.getInt("wordsPerDay", 10);
                    ScheduleResp scheduleResp = body.getData();
                    ((TextView) findViewById(R.id.per_day)).setText(String.valueOf(SharedPreferencesUtil.getInt("wordsPerDay", 10)));
                    ((TextView) findViewById(R.id.book_title)).setText(scheduleResp.getBook().getName());
                    ((TextView) findViewById(R.id.learned)).setText(String.valueOf(scheduleResp.getLearned()));
                    ((TextView) findViewById(R.id.all)).setText(String.valueOf(scheduleResp.getAll()));
                    words = scheduleResp.getAll() - scheduleResp.getLearned();
                    int dayRemained = (int) Math.ceil((double) (words) / SharedPreferencesUtil.getInt("wordsPerDay", 10));
                    ((TextView) findViewById(R.id.days)).setText(String.valueOf(dayRemained));
                    x.image().bind(findViewById(R.id.book_image), scheduleResp.getBook().getCoverImage());
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, dayRemained);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                    String date = dateFormat.format(calendar.getTimeInMillis());
                    ((TextView) findViewById(R.id.finish_day)).setText(date);
                    ((TextView) findViewById(R.id.minutes_per_day)).setText(String.valueOf((int) Math.ceil(SharedPreferencesUtil.getInt("wordsPerDay", 10) / 2)));
                    initSelectNumWheel();
                    initSelectDayWheel();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ScheduleResp>> call, Throwable t) {
                Toast.makeText(UpdateScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void initBinding() {
        ScheduleRequest scheduleRequest = RequestModule.SCHEDULE_REQUEST;
        findViewById(R.id.book_image).setOnClickListener(v -> {
            Intent toMyBookPage = new Intent();
            toMyBookPage.setClass(this, MyBookActivity.class);
            startActivity(toMyBookPage);
        });
        findViewById(R.id.btn_reset).setOnClickListener(v -> {
            scheduleRequest.resetSchedule().enqueue(new Callback<BaseResponse<SimpleScheduleResp>>() {
                @Override
                public void onResponse(Call<BaseResponse<SimpleScheduleResp>> call, Response<BaseResponse<SimpleScheduleResp>> response) {
                    BaseResponse<SimpleScheduleResp> body = response.body();
                    if (body == null || body.getCode() != 200) {
                        onFailure(call, new Throwable("获取计划失败"));
                    } else {
                        SimpleScheduleResp simpleScheduleResp = body.getData();
                        SharedPreferencesUtil.putLong("scheduleId", simpleScheduleResp.getId());
                        SharedPreferencesUtil.putLong("bookId", simpleScheduleResp.getBookId());
                        SharedPreferencesUtil.putInt("wordsPerDay", simpleScheduleResp.getWordsPerDay());
                        Toast.makeText(UpdateScheduleActivity.this, "重置成功", Toast.LENGTH_SHORT).show();
                        initSchedule();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<SimpleScheduleResp>> call, Throwable t) {
                    Toast.makeText(UpdateScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });

        findViewById(R.id.save_plan).setOnClickListener(v -> {
            scheduleRequest.updateSchedule(new UpdateScheduleReq(
                    SharedPreferencesUtil.getLong("scheduleId", -1L),
                    currentWordPerDay
            )).enqueue(new Callback<BaseResponse<Object>>() {
                @Override
                public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                    BaseResponse<Object> body = response.body();
                    if (body == null || body.getCode() != 200) {
                        onFailure(call, new Throwable("保存计划失败"));
                    } else {
                        SharedPreferencesUtil.putInt("wordsPerDay", currentWordPerDay);
                        initSchedule();
                        Toast.makeText(UpdateScheduleActivity.this, "保存计划成功", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                    Toast.makeText(UpdateScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
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
            bar.setTitle("学习计划");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initSelectNumWheel() {
        WheelView selectNumView = findViewById(R.id.select_num);
        selectNumView.setCyclic(false);
        selectNumView.setAlphaGradient(true);

        final List<String> mOptionsItems = new ArrayList<>();
        for (Integer num : values) {
            mOptionsItems.add(num.toString() + "个");
        }
        selectNumView.setAdapter(new ArrayWheelAdapter<>(mOptionsItems));
        selectNumView.setCurrentItem(getCurrentNumItem());

        selectNumView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                wordPerDay = values[index];
                updateSelectDayWheel();
                String s = mOptionsItems.get(index);
                currentWordPerDay = Integer.parseInt(s.substring(0, s.length() - 1));
            }
        });
    }

    private void initSelectDayWheel() {
        WheelView selectDayView = findViewById(R.id.select_day);
        selectDayView.setCyclic(false);
        selectDayView.setAlphaGradient(true);

        final List<String> mOptionsItems = new ArrayList<>();
        days = getDayList();
        for (Integer num : days) {
            mOptionsItems.add(num.toString() + "天");
        }
        selectDayView.setAdapter(new ArrayWheelAdapter<>(mOptionsItems));
        selectDayView.setCurrentItem(getCurrentDayItem(days));

        selectDayView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                updateSelectNumWheel(index);
                String s = mOptionsItems.get(index);
                currentWordPerDay = Integer.parseInt(s.substring(0, s.length() - 1));
            }
        });
    }

    private void updateSelectDayWheel() {
        WheelView selectDayView = findViewById(R.id.select_day);
        selectDayView.setCurrentItem(getCurrentDayItem(days));
    }

    private void updateSelectNumWheel(int index) {
        WheelView selectNumView = findViewById(R.id.select_num);

        int day_planned = days.get(index);
        int least_words_per_day = (int) Math.ceil((double) words / day_planned);
        for (int i = 0; i < values.length; i++) {
            if (values[i] >= least_words_per_day) {
                selectNumView.setCurrentItem(i);
                break;
            }
        }
    }

    private int getCurrentDayItem(List<Integer> days) {
        for (int index = 0; index < days.size(); index++) {
            if (days.get(index) == (int) Math.ceil((double) words / wordPerDay)) {
                return index;
            }
        }
        return 0;
    }

    private int getCurrentNumItem() {
        for (int index = 0; index < values.length; index++) {
            if (Objects.equals(wordPerDay, values[index])) {
                return index;
            }
        }
        return 0;
    }

    private List<Integer> getDayList() {
        LinkedHashSet<Integer> daySet = new LinkedHashSet<>();

        for (int index = values.length - 1; index >= 0; index--) {
            int day = (int) Math.ceil((double) words / values[index]);
            daySet.add(day);
        }

        return new ArrayList<>(daySet);
    }

}
