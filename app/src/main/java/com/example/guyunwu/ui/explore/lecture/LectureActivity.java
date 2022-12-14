package com.example.guyunwu.ui.explore.lecture;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.api.ArticleRequest;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.databinding.ActivityLectureBinding;
import com.example.guyunwu.ui.explore.article.Article;
import com.example.guyunwu.ui.explore.article.ArticleAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static com.example.guyunwu.util.UiUtil.isScrollToBottom;

public class

LectureActivity extends AppCompatActivity {

    private ActivityLectureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLectureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar();
        setupArticle();
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

    private final List<Article> articleList = new ArrayList<>();

    private int page = 0;
    private volatile boolean loading = false;
    private volatile boolean reachEnd = false;

    private synchronized void reloadArticle() {
        page = 0;
        reachEnd = false;
        articleList.clear();
        fetchArticle(true);
    }

    private synchronized void fetchArticle(boolean reload) {
        if (loading || reachEnd) {
            return;
        }
        loading = true;

        ArticleRequest articleRequest = RequestModule.ARTICLE_REQUEST;

        articleRequest.articles(page, 10, "classroom").enqueue(new Callback<BaseResponse<List<Article>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Article>>> call, Response<BaseResponse<List<Article>>> response) {
                BaseResponse<List<Article>> body = response.body();
                if (body != null && body.getData() != null) {
                    int size = articleList.size();
                    List<Article> res = body.getData();
                    if (res.size() == 0) {
                        reachEnd = true;
                    } else {
                        articleList.addAll(res);
                        if (binding != null) {
                            if (reload) {
                                binding.lectureArticlePreviewRecyclerView.getAdapter().notifyDataSetChanged();
                            } else {
                                binding.lectureArticlePreviewRecyclerView.getAdapter().notifyItemRangeInserted(size, res.size());
                            }
                            binding.lectureArticlePreviewRecyclerView.scrollToPosition(0);
                        }
                    }
                }
                page++;
                loading = false;
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Article>>> call, Throwable t) {
                loading = false;
            }
        });

    }

    private void setupArticle() {
        // 获取“小课堂”数据
        RecyclerView recyclerView = binding.lectureArticlePreviewRecyclerView;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ArticleAdapter adapter = new ArticleAdapter(articleList);
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
                    fetchArticle(false);
                }
            }
        });
    }
}
