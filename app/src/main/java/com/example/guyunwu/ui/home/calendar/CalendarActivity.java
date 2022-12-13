package com.example.guyunwu.ui.home.calendar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.guyunwu.R;
import com.example.guyunwu.ui.home.wordbook.WordBook;
import com.example.guyunwu.ui.home.wordbook.WordBookAdapter;
import com.example.guyunwu.ui.home.wordbook.WordBookProvider;

import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

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

    private void initRecyclerView() {
        // 获取实词数据
        List<WordBook> wordBooks = WordBookProvider.getWordBooks();
        RecyclerView recyclerView = findViewById(R.id.word_book_list);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        WordBookAdapter adapter = new WordBookAdapter(wordBooks);
        recyclerView.setAdapter(adapter);
    }
}
