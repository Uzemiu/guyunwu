package com.example.guyunwu.ui.home.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.guyunwu.api.req.DateReq;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.ui.home.wordbook.WordBookAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_calendar);
        initBinding();
        initActionBar();
        initRecyclerView();
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
                initRecyclerView1(year, month + 1, dayOfMonth);
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

    private void initRecyclerView1(int year, int month, int dayOfMonth) {
        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;
        DateReq dateReq = new DateReq(year, month, dayOfMonth);

        learnRequest.learnRecord(dateReq).enqueue(new Callback<BaseResponse<List<WordWithBook>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WordWithBook>>> call, Response<BaseResponse<List<WordWithBook>>> response) {
                BaseResponse<List<WordWithBook>> body = response.body();
//                if (body == null || body.getCode() != 200) {
//                    onFailure(call, new Throwable("登录失败"));
//                } else {
//                    int data = body.getData();
//                    binding.hasLearn.setText(data + " ");
//                }
                System.out.println(body.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WordWithBook>>> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void initRecyclerView() {
        List<WordWithBook> wordBooks = null;
        RecyclerView recyclerView = findViewById(R.id.word_book_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        WordBookAdapter adapter = new WordBookAdapter(wordBooks);
        recyclerView.setAdapter(adapter);
    }
}
