package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.databinding.FragmentLearnWithoutFinishBinding;
import com.example.guyunwu.ui.home.study.LearnActivity;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;

public class LearnWithoutFinishFragment extends Fragment {

    private FragmentLearnWithoutFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLearnWithoutFinishBinding.inflate(inflater, container, false);
        initBinding();
        Bundle bundle = getArguments();
        if (bundle != null) {
            int review = bundle.getInt("review");
            int learn = bundle.getInt("learn");
            binding.needToReview.setText(review + " ");
            binding.needToLearn.setText(learn + " ");
            binding.minutes.setText(String.valueOf((learn + review) / 2));
        }
        if (!SharedPreferencesUtil.contain("scheduleId")) {
            binding.btnStartLearn.setText("当前还未添加计划");
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initBinding() {
        binding.btnStartLearn.setOnClickListener(v -> {
            if (SharedPreferencesUtil.contain("scheduleId")) {
                Intent toLearnPage = new Intent(getActivity(), LearnActivity.class);
                startActivity(toLearnPage);
            } else {
                Intent toMyBookPage = new Intent(getActivity(), MyBookActivity.class);
                startActivity(toMyBookPage);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
