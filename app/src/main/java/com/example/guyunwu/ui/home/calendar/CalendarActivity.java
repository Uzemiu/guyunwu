package com.example.guyunwu.ui.home.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.LearnRecordResp;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.ui.home.wordbook.WordBookAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_calendar);
        initBinding();
        initActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //返回键的id
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBinding() {
        CalendarView calendarview = findViewById(R.id.calendarView);
        TextView text = findViewById(R.id.theDay);
        Calendar calendar = Calendar.getInstance();
        text.setText(calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");

        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                text.setText(month + 1 + "月" + dayOfMonth + "日");
                initRecyclerView(new Date(year - 1900, month, dayOfMonth));
            }
        });
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("学习日历");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView(Date date) {
        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;
        learnRequest.learnRecord(date).enqueue(new Callback<BaseResponse<LearnRecordResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<LearnRecordResp>> call, Response<BaseResponse<LearnRecordResp>> response) {
                BaseResponse<LearnRecordResp> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("登录失败"));
                } else {
                    List<WordWithBook> wordBooks = body.getData().getWords();
                    if (wordBooks.size() != 0) {
                        findViewById(R.id.studyRecord).setVisibility(View.INVISIBLE);
                    } else {
                        findViewById(R.id.studyRecord).setVisibility(View.VISIBLE);
                    }
                    if (body.getData().getIsClocked()) {
                        ((TextView) findViewById(R.id.finished)).setText("已完成");
                    } else {
                        ((TextView) findViewById(R.id.finished)).setText("未完成");
                    }
                    RecyclerView recyclerView = findViewById(R.id.word_book_list);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    WordBookAdapter adapter = new WordBookAdapter(wordBooks);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LearnRecordResp>> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
