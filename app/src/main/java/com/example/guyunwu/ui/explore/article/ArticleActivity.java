package com.example.guyunwu.ui.explore.article;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.guyunwu.columnconverter.LocalDateTimeColumnConverter;
import com.example.guyunwu.databinding.ActivityArticleBinding;

import org.xutils.x;

import java.time.format.DateTimeFormatter;

import io.github.mthli.knife.KnifeParser;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = "ArticleActivity";

    private ActivityArticleBinding binding;

    private ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initBinding();

        Article article = (Article) getIntent().getSerializableExtra("article");
        articleViewModel.getMArticle().setValue(article);
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
            bar.setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initBinding(){
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);

        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        articleViewModel.getMArticle().observe(this, article -> {
            if(TextUtils.isEmpty(article.getCoverImage())){
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
            binding.articlePublishDate.setText( article.getPublishDate()
                    .atZone(LocalDateTimeColumnConverter.ZONE_OFFSET).format(formatter));

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(article.getTitle());
            }
        });
    }

}