package com.example.guyunwu.ui.home.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.CollectionRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.ui.home.study.LearnActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class WordBookActivity extends AppCompatActivity {

    private static final String TAG = "WordBookActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_book);
        initActionBar();
        initRecyclerView();
        findViewById(R.id.btn_learn).setOnClickListener(v -> {
            Intent toLearnPage = new Intent();
            toLearnPage.setClass(this, LearnActivity.class);
            startActivity(toLearnPage);
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
            bar.setTitle("字词本");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView() {
        // 获取图书数据
        CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;

        collectionRequest.myWord().enqueue(new Callback<BaseResponse<List<WordWithBook>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WordWithBook>>> call, Response<BaseResponse<List<WordWithBook>>> response) {
                BaseResponse<List<WordWithBook>> body1 = response.body();
                if (body1 == null || body1.getCode() != 200) {
                    onFailure(call, new Throwable("请求失败"));
                } else {
                    List<WordWithBook> wordBooks = body1.getData();
                    RecyclerView recyclerView = findViewById(R.id.word_book_list);
                    StaggeredGridLayoutManager layoutManager = new
                            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    WordBookAdapter adapter = new WordBookAdapter(wordBooks);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WordWithBook>>> call, Throwable t) {
                Toast.makeText(WordBookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

}
