package com.example.guyunwu.ui.explore.lecture;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guyunwu.R;
import com.example.guyunwu.ui.explore.ExploreDataProvider;
import com.example.guyunwu.ui.explore.article.Article;
import com.example.guyunwu.ui.explore.article.ArticleAdapter;

import java.util.List;

public class LectureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        initActionBar();
        initRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("小课堂");
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView(){
        // 获取“小课堂”数据
        List<Article> articles = ExploreDataProvider.getArticles();
        RecyclerView recyclerView = findViewById(R.id.lecture_article_preview_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ArticleAdapter adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);
    }
}