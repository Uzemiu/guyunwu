package com.example.guyunwu.ui.home.wordbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.guyunwu.R;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.ui.home.study.LearnFragment;
import com.example.guyunwu.ui.home.study.LearnFragmentAdapter;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WordBookLearnActivity extends AppCompatActivity {

    private static final String TAG = "WordBookLearnActivity";

    ViewPager2 viewPager;
    @Getter
    private int currentPosition = 0;

    private int DSI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initActionBar();
        List<WordWithBook> words = (List<WordWithBook>) getIntent().getSerializableExtra("words");
        if (words == null || words.size() == 0) {
            return;
        }
        initPager(words);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentPosition", currentPosition + "");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String c = savedInstanceState.getString("currentPosition");
            currentPosition = Integer.parseInt(c);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initPager(List<WordWithBook> words) {
        viewPager = findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            fragments.add(WordBookLearnFragment.newInstance(words.get(i).getBook(), words.get(i), viewPager, i + 1, words.size(), fragments));
        }
        LearnFragmentAdapter learnFragmentAdapter = new LearnFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(learnFragmentAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                super.onPageSelected(position);
                LearnFragment learnFragment = (LearnFragment) fragments.get(position);
                viewPager.setUserInputEnabled(learnFragment.isTapped());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        DSI += 1;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        DSI += 2;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (viewPager.getCurrentItem() == viewPager.getAdapter().getItemCount() - 1 && DSI == 1) {
                            Intent intent = new Intent(WordBookLearnActivity.this, WordBookActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        DSI = 0;
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //????????????id
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
}
