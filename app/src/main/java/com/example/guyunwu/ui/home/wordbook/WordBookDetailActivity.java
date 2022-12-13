package com.example.guyunwu.ui.home.wordbook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.CollectionRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.databinding.ActivityWordBookDetailBinding;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordBookDetailActivity extends AppCompatActivity {

    private static final String TAG = "WordBookDetailActivity";

    private ActivityWordBookDetailBinding binding;

    private WordBookViewModel wordBookViewModel;

    private boolean isStar = true;

    private WordWithBook word;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initBinding();

        WordWithBook wordBook = (WordWithBook) getIntent().getSerializableExtra("wordBook");
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
            word = wordBook;
            binding.wordBookTitle.setText(wordBook.getKeyWord());
            binding.wordBookContent.setText(wordBook.getContent());
            Integer correctAnswer = wordBook.getCorrectAnswer();
            String stringCorrectAnswer = "";
            switch (correctAnswer) {
                case 0:
                    stringCorrectAnswer = wordBook.getAnswerA();
                    break;
                case 1:
                    stringCorrectAnswer = wordBook.getAnswerB();
                    break;
                case 2:
                    stringCorrectAnswer = wordBook.getAnswerC();
                    break;
                case 3:
                    stringCorrectAnswer = wordBook.getAnswerD();
                    break;
            }
            binding.wordBookKey.setText(stringCorrectAnswer);
            binding.wordBookReference.setText("——" + wordBook.getBook().getName());
            binding.wordBookTranslation.setText(wordBook.getTranslate());
        });
        binding.btnWordBookStar.setOnClickListener(v -> {
            CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;
            if (isStar) {
                collectionRequest.cancelWord(word.getWordId()).enqueue(new Callback<BaseResponse<Object>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                        BaseResponse<Object> body1 = response.body();
                        if (body1 == null || body1.getCode() != 200) {
                            onFailure(call, new Throwable("请求失败"));
                        } else {
                            isStar = !isStar;
                            if (isStar) {
                                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_fill_24dp);
                            } else {
                                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_24dp);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                        Toast.makeText(WordBookDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
            } else {
                collectionRequest.starWord(word.getWordId()).enqueue(new Callback<BaseResponse<Object>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                        BaseResponse<Object> body1 = response.body();
                        if (body1 == null || body1.getCode() != 200) {
                            onFailure(call, new Throwable("请求失败"));
                        } else {
                            isStar = !isStar;
                            if (isStar) {
                                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_fill_24dp);
                            } else {
                                binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_24dp);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                        Toast.makeText(WordBookDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // todo with isStar
    }
}
