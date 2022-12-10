package com.example.guyunwu.ui.home.wordbook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.guyunwu.R;
import com.example.guyunwu.databinding.ActivityWordBookDetailBinding;
import org.jetbrains.annotations.NotNull;

public class WordBookDetailActivity extends AppCompatActivity {

    private static final String TAG = "WordBookDetailActivity";

    private ActivityWordBookDetailBinding binding;

    private WordBookViewModel wordBookViewModel;

    private boolean isStar = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initBinding();

        WordBook wordBook = (WordBook) getIntent().getSerializableExtra("wordBook");
        wordBookViewModel.getMWordBook().setValue(wordBook);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("isStar", isStar + "");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String c = savedInstanceState.getString("isStar");
            if (c.equals("true")) {
                isStar = true;
                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_fill_24dp);
            } else {
                isStar = false;
                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_24dp);
            }
        }
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initBinding() {
        wordBookViewModel = new ViewModelProvider(this).get(WordBookViewModel.class);

        binding = ActivityWordBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wordBookViewModel.getMWordBook().observe(this, wordBook -> {
            binding.wordBookTitle.setText(wordBook.getTitle());
            binding.wordBookContent.setText(wordBook.getContent());
            binding.wordBookKey.setText(wordBook.getKey());
            binding.wordBookReference.setText(wordBook.getReference());
            binding.wordBookTranslation.setText(wordBook.getTranslation());
        });
        binding.btnWordBookStar.setOnClickListener(v -> {
            isStar = !isStar;
            if (isStar) {
                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_fill_24dp);
            } else {
                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_24dp);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // todo with isStar
    }
}
