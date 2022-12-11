package com.example.guyunwu.ui.home.study;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.guyunwu.R;
import com.example.guyunwu.api.resp.WordResp;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initActionBar();
        initPager();
    }

    private void initPager() {
        viewPager = findViewById(R.id.viewpager);

        List<Fragment> fragments = new ArrayList<>();
        List<WordResp> words = LearnDataProvider.getWords();
        for (WordResp word : words) {
            fragments.add(LearnFragment.newInstance(word, viewPager));
        }
        LearnFragmentAdapter learnFragmentAdapter = new LearnFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(learnFragmentAdapter);
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
            bar.setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
