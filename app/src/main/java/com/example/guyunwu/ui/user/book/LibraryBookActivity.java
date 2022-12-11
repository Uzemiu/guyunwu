package com.example.guyunwu.ui.user.book;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.guyunwu.databinding.ActivityLibraryBookBinding;
import io.github.mthli.knife.KnifeParser;
import org.xutils.x;

public class LibraryBookActivity extends AppCompatActivity {

    private static final String TAG = "LibraryBookActivity";

    private ActivityLibraryBookBinding binding;

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

    private void initBinding() {
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        binding = ActivityLibraryBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookViewModel.getMBook().observe(this, book -> {
            if (TextUtils.isEmpty(book.getCoverImage())) {
                binding.bookHeader.setVisibility(View.GONE);
            } else {
                x.image().bind(binding.bookCoverImage, book.getCoverImage());
            }
            if (book.getAuthor() != null) {
                binding.bookAuthorName.setText('[' + book.getAuthor().getDynasty() + ']' + book.getAuthor().getName());
            }
            binding.bookTitle.setText(book.getTitle());
            binding.bookContent.setText(KnifeParser.fromHtml(book.getContent()));
            binding.bookPress.setText(book.getPress());

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(book.getTitle());
            }
        });

        binding.addBook.setOnClickListener(v -> {
            // TODO
        });
    }
}
