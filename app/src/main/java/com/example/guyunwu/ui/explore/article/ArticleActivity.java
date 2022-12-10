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
import com.example.guyunwu.columnconverter.LocalDateTimeColumnConverter;
import com.example.guyunwu.databinding.ActivityArticleBinding;
import com.example.guyunwu.databinding.DialogCommentBinding;
import com.example.guyunwu.repository.CommentQuery;
import com.example.guyunwu.repository.CommentRepository;
import com.example.guyunwu.repository.Pageable;
import com.example.guyunwu.ui.explore.comment.Comment;
import com.example.guyunwu.ui.explore.comment.CommentAdapter;

import org.xutils.db.Selector;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.github.mthli.knife.KnifeParser;

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

    private long totalComments = 0;

    private int page = 1;

    private final int size = 10;

    private synchronized void fetchComment() {
        CommentQuery query = new CommentQuery();
        query.setArticleId(articleViewModel.getArticleId());
        totalComments = commentRepository.count(query);

        if (commentList.size() >= totalComments) {
            return;
        }

        Selector.OrderBy orderByLikes = new Selector.OrderBy("likes", true);
        Selector.OrderBy orderById = new Selector.OrderBy("id", true);
        Pageable pageable = new Pageable();
        pageable.setOrderByList(Arrays.asList(orderByLikes, orderById));
        pageable.setPage(page++);
        pageable.setSize(size);

        int size = commentList.size();
        List<Comment> cs = commentRepository.query(query, pageable);
        commentList.addAll(cs);
        binding.articleCommentList.getAdapter().notifyItemRangeChanged(size, cs.size());
    }

    private void sendComment(String text) {
        Comment comment = new Comment();
        comment.setArticleId(articleViewModel.getArticleId());
        comment.setContent(text);
        comment.setLikes(0L);
        Author author = new Author();
        author.setId(1);
        author.setAvatar("https://avatars.githubusercontent.com/u/1209810?v=4");
        author.setName("guyunwu");
        comment.setAuthor(author);
        comment.setPublishDate(LocalDateTime.now());
        commentRepository.save(comment);

        commentList.add(0, comment);
        binding.articleCommentList.getAdapter().notifyItemInserted(0);
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

    private void setupArticle() {
        Article article = (Article) getIntent().getSerializableExtra("article");
        articleViewModel.getMArticle().setValue(article);
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
                binding.articleAuthorName.setText(article.getAuthor().getName());
            }
            binding.articleTitle.setText(article.getTitle());
            binding.articleContent.setText(KnifeParser.fromHtml(article.getContent()));
            if(article.getPublishDate() != null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                binding.articlePublishDate.setText(dateFormat.format(article.getPublishDate()));
            }
            binding.articleReads.setText(String.valueOf(article.getReads()));

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(article.getTitle());
            }
        });
    }

}