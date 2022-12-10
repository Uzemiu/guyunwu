package com.example.guyunwu.ui.explore.daily;

import static com.example.guyunwu.util.UiUtil.isScrollToBottom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.CollectionRequest;
import com.example.guyunwu.api.RequestModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private int page = 0;
    private volatile boolean reachEnd = false;
    private volatile boolean loading = false;

    private synchronized void fetchDailySentence(){
        if(reachEnd || loading) return;

        loading = true;
        CollectionRequest request = RequestModule.COLLECTION_REQUEST;

        int size = dailySentenceList.size();
        request.dailySentences(page, 10).enqueue(new Callback<BaseResponse<List<DailySentence>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<DailySentence>>> call, Response<BaseResponse<List<DailySentence>>> response) {
                BaseResponse<List<DailySentence>> baseResponse = response.body();
                if (baseResponse != null) {
                    List<DailySentence> dailySentences = baseResponse.getData();
                    if (dailySentences != null && dailySentences.size() > 0) {
                        dailySentenceList.addAll(dailySentences);
                        recyclerView.post(() -> adapter.notifyItemRangeChanged(size, dailySentences.size()));
                    } else {
                        reachEnd = true;
                    }
                }
                page++;
                loading = false;
            }

            @Override
            public void onFailure(Call<BaseResponse<List<DailySentence>>> call, Throwable t) {
                Toast.makeText(DailySentenceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
                loading = false;
            }
        });
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
                    fetchDailySentence();
                }
            }
        });

        fetchDailySentence();
    }

}