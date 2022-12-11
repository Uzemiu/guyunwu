package com.example.guyunwu.ui.explore.article;

import static com.example.guyunwu.util.UiUtil.isScrollToBottom;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.req.AddCommentReq;
import com.example.guyunwu.databinding.ActivityArticleBinding;
import com.example.guyunwu.databinding.DialogCommentBinding;
import com.example.guyunwu.repository.CommentRepository;
import com.example.guyunwu.ui.explore.comment.Comment;
import com.example.guyunwu.ui.explore.comment.CommentAdapter;

import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.mthli.knife.KnifeParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = "ArticleActivity";

    private ActivityArticleBinding binding;

    private ArticleViewModel articleViewModel;

    private final CommentRepository commentRepository = new CommentRepository();

    private final List<Comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initBinding();
        initRecyclerView();
        setupArticle();
        setupArticleLike();
        setupComment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private int page = 0;
    private volatile boolean loading = false;
    private volatile boolean reachEnd = false;

    private synchronized void fetchComment() {
        if (loading || reachEnd) {
            return;
        }
        loading = true;

        RequestModule.COMMENT_REQUEST.listComment(articleViewModel.getArticleId(), page, 10).enqueue(new Callback<BaseResponse<List<Comment>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Comment>>> call, Response<BaseResponse<List<Comment>>> response) {
                BaseResponse<List<Comment>> body = response.body();
                if (body != null && body.getData() != null) {
                    int size = commentList.size();
                    List<Comment> res = body.getData();
                    if (res.size() == 0) {
                        reachEnd = true;
                    } else {
                        commentList.addAll(res);
                        binding.articleCommentList.getAdapter().notifyItemRangeInserted(size, res.size());
                    }
                }
                page++;
                loading = false;
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Comment>>> call, Throwable t) {
                Toast.makeText(ArticleActivity.this, "获取留言失败:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                loading = false;
            }
        });
    }

    private volatile boolean sending = false;

    private void sendComment(String text) {
        if (sending) {
            return;
        }
        sending = true;

        AddCommentReq req = new AddCommentReq();
        req.setArticleId(Long.valueOf(articleViewModel.getArticleId()));
        req.setContent(text);
        RequestModule.COMMENT_REQUEST.addComment(req).enqueue(new Callback<BaseResponse<Comment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Comment>> call, Response<BaseResponse<Comment>> response) {
                sending = false;
                BaseResponse<Comment> body = response.body();
                if (body == null || body.getData() == null) {
                    return;
                }
                commentList.add(0, body.getData());
                binding.articleCommentList.getAdapter().notifyItemInserted(0);
            }

            @Override
            public void onFailure(Call<BaseResponse<Comment>> call, Throwable t) {
                sending = false;
                Toast.makeText(ArticleActivity.this, "发表留言失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupComment() {
        binding.articleCommentAdd.setOnClickListener(v -> {
            // open comment dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("发表留言");

            DialogCommentBinding binding = DialogCommentBinding.inflate(getLayoutInflater());
            builder.setView(binding.getRoot());
            EditText editText = binding.articleCommentEdit;
            builder.setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
                String comment = editText.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    Toast.makeText(this, "留言不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendComment(comment);
            });
            builder.setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        fetchComment();
        binding.articleOuterScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (isScrollToBottom(v, 500)) {
                        fetchComment();
                    }
                }
        );
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.articleCommentList;
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
    }

    private void setupArticleLike() {
        final boolean[] likeLoading = {true};
        Callback<BaseResponse<Boolean>> likeCallback = new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                BaseResponse<Boolean> body = response.body();
                if (body != null) {
                    articleViewModel.getMLike().setValue(Boolean.TRUE.equals(body.getData()));
                }
                likeLoading[0] = false;
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                Toast.makeText(ArticleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                likeLoading[0] = false;
            }
        };

        RequestModule.ARTICLE_REQUEST.getArticleLike(articleViewModel.getArticleId()).enqueue(likeCallback);
        binding.articleLikeBtn.setOnClickListener(v -> {
            if (likeLoading[0]) {
                return;
            }
            likeLoading[0] = true;
            RequestModule.ARTICLE_REQUEST.doLikeArticle(articleViewModel.getArticleId()).enqueue(new Callback<BaseResponse<Boolean>>() {
                @Override
                public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                    BaseResponse<Boolean> body = response.body();
                    if (body != null) {
                        boolean res = Boolean.TRUE.equals(body.getData());
                        articleViewModel.getMLike().setValue(res);

                        Article article = articleViewModel.getMArticle().getValue();
                        article.setLikes(article.getLikes() + (res ? 1 : -1));
                        binding.articleLikes.setText(String.valueOf(article.getLikes()));
                    }
                    likeLoading[0] = false;
                }

                @Override
                public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                    Toast.makeText(ArticleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    likeLoading[0] = false;
                }
            });
        });
    }

    private void setupArticle() {
        Article article = (Article) getIntent().getSerializableExtra("article");
        articleViewModel.getMArticle().setValue(article);

        RequestModule.ARTICLE_REQUEST.getArticle(article.getId()).enqueue(new Callback<BaseResponse<Article>>() {
            @Override
            public void onResponse(Call<BaseResponse<Article>> call, Response<BaseResponse<Article>> response) {
                BaseResponse<Article> body = response.body();
                if (body != null) {
                    Article article = body.getData();
                    articleViewModel.getMArticle().setValue(article);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Article>> call, Throwable t) {
                Toast.makeText(ArticleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initBinding() {
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);

        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        articleViewModel.getMArticle().observe(this, article -> {
            if (TextUtils.isEmpty(article.getCoverImage())) {
                binding.articleHeader.setVisibility(View.GONE);
            } else {
                x.image().bind(binding.articleCoverImage, article.getCoverImage());
            }
            if (article.getAuthor() != null) {
                x.image().bind(binding.articleAuthorAvatar, article.getAuthor().getAvatar());
                binding.articleAuthorName.setText(article.getAuthor().getUsername());
            }
            binding.articleTitle.setText(article.getTitle());
            binding.articleContent.setText(KnifeParser.fromHtml(article.getContent()));
            if (article.getPublishDate() != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                binding.articlePublishDate.setText(dateFormat.format(article.getPublishDate()));
            }
            binding.articleReads.setText(String.valueOf(article.getReads()));
            binding.articleLikes.setText(String.valueOf(article.getLikes()));

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(article.getTitle());
            }
        });
        articleViewModel.getMLike().observe(this, like -> {
            if (like) {
                binding.articleLikeIcon.setImageResource(R.drawable.article_like_fill);
            } else {
                binding.articleLikeIcon.setImageResource(R.drawable.article_like);
            }
        });
    }

}