package com.example.guyunwu.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.databinding.FragmentLearnWithoutFinishBinding;
import com.example.guyunwu.ui.home.study.LearnActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;

public class LearnWithoutFinishFragment extends Fragment {

    private static final String TAG = "LearnWithoutFinishFragment";

    private FragmentLearnWithoutFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLearnWithoutFinishBinding.inflate(inflater, container, false);
        initBinding();
        initView();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initBinding() {
        binding.btnStartLearn.setOnClickListener(v -> {
            Intent toLearnPage = new Intent();
            toLearnPage.setClass(getActivity(), LearnActivity.class);
            startActivity(toLearnPage);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        int needToReview = SharedPreferencesUtil.getInt("needToReview", 0);
        int needToLearn = SharedPreferencesUtil.getInt("needToLearn", 0);
        int minutes = SharedPreferencesUtil.getInt("minutes", 0);
        binding.needToLearn.setText(needToLearn + " ");
        binding.needToReview.setText(needToReview + " ");
        binding.minutes.setText(String.valueOf(minutes));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
