package com.example.guyunwu.ui.home.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.fastjson2.JSON;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.CollectionRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.Word;
import com.example.guyunwu.databinding.FragmentLearnBinding;
import com.example.guyunwu.ui.user.book.Book;
import com.google.android.material.card.MaterialCardView;
import io.github.mthli.knife.KnifeParser;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class LearnFragment extends Fragment {
    enum Answer {
        A, B, C, D
    }

    private Answer key;

    private static final String TAG = "LearnFragment";

    private static final int correct_color = 0xFFD3F2D2;

    private static final int incorrect_color = 0xFFFBD1D2;

    private FragmentLearnBinding binding;

    private static final String WORD = "word";

    private static final String BOOK = "book";

    private Word word;

    private Book book;

    @Setter
    private ViewPager2 viewPager2;

    @Getter
    private boolean isTapped = false;

    @Setter
    private int currentPage;

    @Setter
    private int allPage;

    private boolean isStar = false;

    @Setter
    private List<Fragment> fragmentList;

    public static LearnFragment newInstance(Book book, Word word, ViewPager2 viewPager, int currentPage, int allPage, List<Fragment> fragmentList) {
        LearnFragment fragment = new LearnFragment();
        fragment.setViewPager2(viewPager);
        fragment.setCurrentPage(currentPage);
        fragment.setAllPage(allPage);
        fragment.setFragmentList(fragmentList);
        Bundle bundle = new Bundle();
        bundle.putString(WORD, JSON.toJSONString(word));
        bundle.putString(BOOK, JSON.toJSONString(book));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = JSON.parseObject(getArguments().getString(WORD), Word.class);
            book = JSON.parseObject(getArguments().getString(BOOK), Book.class);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentLearnBinding.inflate(inflater, container, false);
        }
        View root = binding.getRoot();
        initView();
        initAnswerView();
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        binding.questionKey.setText(word.getKeyWord());
        int index = ordinalIndexOf(word.getContent(), word.getKeyWord(), word.getKeyIndex());
        String stringBuilder = word.getContent().substring(0, index) +
                "<b><font color='black'>" +
                word.getKeyWord() +
                "</font></b>" +
                word.getContent().substring(index + word.getKeyWord().length());

        binding.questionContent.setText(KnifeParser.fromHtml(stringBuilder));
        binding.questionReference.setText("——" + book.getName());
        binding.textAnswerA.setText(word.getAnswerA());
        binding.textAnswerB.setText(word.getAnswerB());
        binding.textAnswerC.setText(word.getAnswerC());
        binding.textAnswerD.setText(word.getAnswerD());
        binding.questionTranslation.setText(word.getTranslate());
        binding.currentPage.setText(currentPage + "");
        binding.allPage.setText(allPage + "");
        key = Answer.values()[word.getCorrectAnswer()];

        CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;

        collectionRequest.isCollected(word.getWordId()).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                BaseResponse<Boolean> body1 = response.body();
                if (body1 == null || body1.getCode() != 200) {
                    onFailure(call, new Throwable("请求失败"));
                } else {
                    Boolean isStar = body1.getData();
                    if (isStar) {
                        binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_fill_24dp);
                    } else {
                        binding.btnWordBookStar.setBackgroundResource(R.drawable.ic_user_star_24dp);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void initAnswerView() {
        MaterialCardView cardViewA = binding.answerA;
        cardViewA.setOnClickListener(v -> {
            if (!isTapped) {
                selectAnswer(cardViewA, Answer.A);
            }
        });

        MaterialCardView cardViewB = binding.answerB;
        cardViewB.setOnClickListener(v -> {
            if (!isTapped) {
                selectAnswer(cardViewB, Answer.B);
            }
        });

        MaterialCardView cardViewC = binding.answerC;
        cardViewC.setOnClickListener(v -> {
            if (!isTapped) {
                selectAnswer(cardViewC, Answer.C);
            }
        });

        MaterialCardView cardViewD = binding.answerD;
        cardViewD.setOnClickListener(v -> {
            if (!isTapped) {
                selectAnswer(cardViewD, Answer.D);
            }
        });

        binding.btnWordBookStar.setOnClickListener(v -> {
            CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;
            if(isStar) {
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
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
            }

        });
    }

    @SuppressLint("ResourceAsColor")
    private void selectAnswer(MaterialCardView cardView, Answer answer) {
        if (answer == key) {
            cardView.setCardBackgroundColor(correct_color);
            ImageView imageView = cardView.findViewWithTag("image");
            imageView.setImageResource(R.drawable.ic_home_correct_24dp);
            imageView.setVisibility(View.VISIBLE);
        } else {
            cardView.setCardBackgroundColor(incorrect_color);
            ImageView imageView = cardView.findViewWithTag("image");
            imageView.setImageResource(R.drawable.ic_home_incorrect_24dp);
            imageView.setVisibility(View.VISIBLE);
            fragmentList.add(LearnFragment.newInstance(book, word, viewPager2, currentPage, allPage, fragmentList));
            viewPager2.getAdapter().notifyDataSetChanged();
        }
        isTapped = true;
        viewPager2.setUserInputEnabled(true);
        binding.questionTranslation.setAlpha(1);
    }

    private static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

}
