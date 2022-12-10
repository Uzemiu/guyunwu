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

public class LearnWithoutFinishFragment extends Fragment {

    private FragmentLearnWithoutFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLearnWithoutFinishBinding.inflate(inflater, container, false);
        binding.btnStartLearn.setOnClickListener(v -> {
            Intent toLearnPage = new Intent();
            toLearnPage.setClass(getActivity(), LearnActivity.class);
            startActivity(toLearnPage);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
