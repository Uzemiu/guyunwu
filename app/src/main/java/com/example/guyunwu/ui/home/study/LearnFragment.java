package com.example.guyunwu.ui.home.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.fastjson2.JSON;
import com.example.guyunwu.R;
import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.databinding.FragmentLearnBinding;
import com.google.android.material.card.MaterialCardView;
import lombok.Setter;

public class LearnFragment extends Fragment {
    enum Answer {
        A, B, C, D
    }

    private Answer key;

    private static final int correct_color = 0xFFD3F2D2;

    private static final int incorrect_color = 0xFFFBD1D2;

    private FragmentLearnBinding binding;

    private static final String WORD = "word";

    private WordResp word;

    @Setter
    private ViewPager2 viewPager2;

    private boolean isTapped = false;

    public static LearnFragment newInstance(WordResp word, ViewPager2 viewPager) {
        LearnFragment fragment = new LearnFragment();
        fragment.setViewPager2(viewPager);
        Bundle bundle = new Bundle();
        bundle.putString(WORD, JSON.toJSONString(word));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = JSON.parseObject(getArguments().getString(WORD), WordResp.class);
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

    private void initView() {
        binding.questionKey.setText(word.getKeyWord());
        binding.questionContent.setText(word.getContent());
        binding.questionReference.setText(word.getBookName());
        binding.textAnswerA.setText(word.getAnswerA());
        binding.textAnswerB.setText(word.getAnswerB());
        binding.textAnswerC.setText(word.getAnswerC());
        binding.textAnswerD.setText(word.getAnswerD());
        key = Answer.values()[word.getCorrectAnswer()];
    }

    private void initAnswerView() {
        MaterialCardView cardViewA = binding.answerA;
        cardViewA.setOnClickListener(v -> {
            if(!isTapped) {
                selectAnswer(cardViewA, Answer.A);
                isTapped = true;
            }
        });

        MaterialCardView cardViewB = binding.answerB;
        cardViewB.setOnClickListener(v -> {
            if(!isTapped) {
                selectAnswer(cardViewB, Answer.B);
                isTapped = true;
            }
        });

        MaterialCardView cardViewC = binding.answerC;
        cardViewC.setOnClickListener(v -> {
            if(!isTapped) {
                selectAnswer(cardViewC, Answer.C);
                isTapped = true;
            }
        });

        MaterialCardView cardViewD = binding.answerD;
        cardViewD.setOnClickListener(v -> {
            if(!isTapped) {
                selectAnswer(cardViewD, Answer.D);
                isTapped = true;
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
        }
    }


}
