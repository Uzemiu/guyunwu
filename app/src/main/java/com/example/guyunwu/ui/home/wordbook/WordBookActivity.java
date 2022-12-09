package com.example.guyunwu.ui.home.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.R;
import com.example.guyunwu.ui.home.study.LearnActivity;

import java.util.List;

public class WordBookActivity extends AppCompatActivity {

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
        List<WordBook> wordBooks = WordBookProvider.getWordBooks();
        RecyclerView recyclerView = findViewById(R.id.word_book_list);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        WordBookAdapter adapter = new WordBookAdapter(wordBooks);
        recyclerView.setAdapter(adapter);
    }

}
