package com.example.guyunwu.ui.explore.daily;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.guyunwu.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DailySentenceActivity extends AppCompatActivity {

    private static final String TAG = "DailySentenceActivity";
    
    private List<DailySentence> dailySentenceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sentence);

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

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("每日一句");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView() {
        initDailyData();

        RecyclerView recyclerView = findViewById(R.id.daily_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DailySentenceAdapter adapter = new DailySentenceAdapter(dailySentenceList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollToBottom(recyclerView)) {
                    int i = dailySentenceList.size();
                    DailySentence ds = new DailySentence();
                    ds.setId(dailySentenceList.size());
                    ds.setSentence("sentence" + i);
                    ds.setFrom("from" + i);
                    ds.setDate(LocalDateTime.now().minusDays(i));
                    ds.setImageUrl("https://bing.com/th?id=OHR.BambooTreesIndia_ZH-CN3943852151_1920x1080.jpg");
                    dailySentenceList.add(ds);
                    recyclerView.post(() -> adapter.notifyItemInserted(i));
                }
            }
        });
    }

    public static boolean isScrollToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() + 600
                >= recyclerView.computeVerticalScrollRange();
    }

    private void initDailyData() {
        dailySentenceList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DailySentence ds = new DailySentence();
            ds.setId(dailySentenceList.size());
            ds.setSentence("sentence" + i);
            ds.setFrom("from" + i);
            ds.setDate(LocalDateTime.now().minusDays(i));
            ds.setImageUrl("https://bing.com/th?id=OHR.BambooTreesIndia_ZH-CN3943852151_1920x1080.jpg");
            dailySentenceList.add(ds);
        }
    }
}