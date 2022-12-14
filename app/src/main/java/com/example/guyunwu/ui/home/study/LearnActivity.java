package com.example.guyunwu.ui.home.study;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.guyunwu.MainActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.Word;
import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.ui.user.book.Book;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private static final String TAG = "LearnActivity";

    ViewPager2 viewPager;
    @Getter
    private int currentPosition = 0;

    private int DSI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initActionBar();
        initData();
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

    private void initData() {
        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;

        learnRequest.todayWords().enqueue(new Callback<BaseResponse<WordResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<WordResp>> call, Response<BaseResponse<WordResp>> response) {
                BaseResponse<WordResp> body1 = response.body();
                if (body1 == null || body1.getCode() != 200) {
                    onFailure(call, new Throwable("获取失败"));
                } else {
                    List<Word> words = body1.getData().getWords();
                    Book book = body1.getData().getBook();
                    initPager(book, words);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<WordResp>> call, Throwable t) {
                Toast.makeText(LearnActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initPager(Book book, List<Word> words) {
        viewPager = findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            fragments.add(LearnFragment.newInstance(book, words.get(i), viewPager, i + 1, words.size(), fragments));
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
                            Intent intent = new Intent(LearnActivity.this, MainActivity.class);
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
