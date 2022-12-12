package com.example.guyunwu.ui.user.book;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.ScheduleRequest;
import com.example.guyunwu.api.req.ScheduleReq;
import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.databinding.ActivityBookBinding;
import com.example.guyunwu.util.SharedPreferencesUtil;
import io.github.mthli.knife.KnifeParser;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";

    private ActivityBookBinding binding;

    private BookViewModel bookViewModel;

    private int bookId;

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

        binding = ActivityBookBinding.inflate(getLayoutInflater());
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
            binding.bookTitle.setText(book.getName());
//            binding.bookContent.setText(KnifeParser.fromHtml(book.getContent()));
            // todo with nullptr
            binding.bookContent.setText(KnifeParser.fromHtml(book.getIntroduce()));
            binding.bookPress.setText(book.getPress());
            bookId = book.getId();
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(book.getName());
            }
        });

        binding.changePlan.setOnClickListener(v -> {
            ScheduleRequest scheduleRequest = RequestModule.SCHEDULE_REQUEST;

            scheduleRequest.switchSchedule(new ScheduleReq(bookId)).enqueue(new Callback<BaseResponse<WordResp>>() {
                @Override
                public void onResponse(Call<BaseResponse<WordResp>> call, Response<BaseResponse<WordResp>> response) {
                    BaseResponse<WordResp> body = response.body();
                    if (body == null || body.getCode() != 200) {
                        onFailure(call, new Throwable("请求失败"));
                    } else {
                        Toast.makeText(BookActivity.this, "切换成功", Toast.LENGTH_SHORT).show();
                        WordResp wordResp = body.getData();
                        SharedPreferencesUtil.putLong("scheduleId", wordResp.getId());
                        SharedPreferencesUtil.putLong("bookId", wordResp.getBookId());
                        SharedPreferencesUtil.putInt("wordsPerDay", wordResp.getWordsPerDay());
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<WordResp>> call, Throwable t) {
                    Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });
    }
}
