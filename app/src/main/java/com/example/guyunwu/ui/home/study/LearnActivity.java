package com.example.guyunwu.ui.home.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.google.android.material.card.MaterialCardView;

public class LearnActivity extends AppCompatActivity {

    private final Answer key = Answer.A;

    private static final int correct_color = 0xFFD3F2D2;

    private static final int incorrect_color = 0xFFFBD1D2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initActionBar();
        initAnswerView();
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

    private void initAnswerView() {
        MaterialCardView cardViewA = findViewById(R.id.answer_a);
        cardViewA.setOnClickListener(v -> {
            selectAnswer(cardViewA, Answer.A);
        });

        MaterialCardView cardViewB = findViewById(R.id.answer_b);
        cardViewB.setOnClickListener(v -> {
            selectAnswer(cardViewB, Answer.B);
        });

        MaterialCardView cardViewC = findViewById(R.id.answer_c);
        cardViewC.setOnClickListener(v -> {
            selectAnswer(cardViewC, Answer.C);
        });

        MaterialCardView cardViewD = findViewById(R.id.answer_d);
        cardViewD.setOnClickListener(v -> {
            selectAnswer(cardViewD, Answer.D);
        });
    }

    @SuppressLint("ResourceAsColor")
    private void selectAnswer(MaterialCardView cardView, Answer answer) {
        if (answer == key) {
            cardView.setCardBackgroundColor(correct_color);
            ImageView imageView = cardView.findViewWithTag("image");
            imageView.setImageResource(R.drawable.ic_home_corrent_24dp);
            imageView.setVisibility(View.VISIBLE);
        } else {
            cardView.setCardBackgroundColor(incorrect_color);
            ImageView imageView = cardView.findViewWithTag("image");
            imageView.setImageResource(R.drawable.ic_home_incorrent_24dp);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    enum Answer {
        A, B, C, D
    }
}
