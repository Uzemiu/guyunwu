package com.example.guyunwu.ui.user.book;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.guyunwu.columnconverter.LocalDateTimeColumnConverter;
import com.example.guyunwu.databinding.ActivityBookBinding;

import org.xutils.x;

import java.time.format.DateTimeFormatter;

import io.github.mthli.knife.KnifeParser;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";

    private ActivityBookBinding binding;

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initBinding();

        Book book = (Book) getIntent().getSerializableExtra("book");
        bookViewModel.getMBook().setValue(book);
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
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        bookViewModel.getMBook().observe(this, book -> {
            if(TextUtils.isEmpty(book.getCoverImage())){
                binding.bookHeader.setVisibility(View.GONE);
            } else {
                x.image().bind(binding.bookCoverImage, book.getCoverImage());
            }
            if (book.getAuthor() != null) {
                x.image().bind(binding.bookAuthorAvatar, book.getAuthor().getAvatar());
                binding.bookAuthorName.setText(book.getAuthor().getName());
            }
            binding.bookTitle.setText(book.getTitle());
            binding.bookContent.setText(KnifeParser.fromHtml(book.getContent()));
            binding.bookPublishDate.setText( book.getPublishDate()
                    .atZone(LocalDateTimeColumnConverter.ZONE_OFFSET).format(formatter));

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(book.getTitle());
            }
        });
    }
}