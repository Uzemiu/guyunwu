package com.example.guyunwu.ui.explore.daily;

import static com.example.guyunwu.util.UiUtil.isScrollToBottom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guyunwu.R;
import com.example.guyunwu.ui.explore.ExploreDataProvider;

import java.util.ArrayList;
import java.util.List;

public class DailySentenceActivity extends AppCompatActivity {

    private static final String TAG = "DailySentenceActivity";

    private RecyclerView recyclerView;

    private DailySentenceAdapter adapter;

    private List<DailySentence> dailySentenceList;

    public DailySentenceActivity() {
        dailySentenceList = new ArrayList<>();
    }

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

    private void fetchDailySentence(int count){
        int size = dailySentenceList.size();
        List<DailySentence> sentences = ExploreDataProvider.getSentences(size, count);
        dailySentenceList.addAll(sentences);

        recyclerView.post(() -> adapter.notifyItemRangeChanged(size, count));
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.daily_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DailySentenceAdapter(dailySentenceList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollToBottom(recyclerView, 600)) {
                    fetchDailySentence(5);
                }
            }
        });

        fetchDailySentence(10);
    }

}