package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.databinding.FragmentHomeBinding;
import com.example.guyunwu.ui.explore.daily.DailySentenceActivity;
import com.example.guyunwu.ui.explore.lecture.LectureActivity;
import com.example.guyunwu.ui.home.schedule.UpdateScheduleActivity;
import com.example.guyunwu.ui.home.study.LearnActivity;
import com.example.guyunwu.ui.home.wordbook.WordBookActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initRouter();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRouter() {
        binding.schedule.setOnClickListener(v -> {
            Intent toSchedulePage = new Intent();
            toSchedulePage.setClass(getActivity(), UpdateScheduleActivity.class);
            startActivity(toSchedulePage);
        });
        binding.startLearn.setOnClickListener(v -> {
            Intent toLearnPage = new Intent();
            toLearnPage.setClass(getActivity(), LearnActivity.class);
            startActivity(toLearnPage);
        });
        binding.dailySentence.setOnClickListener(v -> {
            Intent toDailyPage = new Intent();
            toDailyPage.setClass(getActivity(), DailySentenceActivity.class);
            startActivity(toDailyPage);
        });
        binding.lecture.setOnClickListener(v -> {
            Intent toLecture = new Intent();
            toLecture.setClass(getActivity(), LectureActivity.class);
            startActivity(toLecture);
        });
        binding.ecnu.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.ecnu.edu.cn/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        binding.guyunActivity.setOnClickListener(v -> {
            Intent toBlankPage = new Intent();
            toBlankPage.setClass(getActivity(), BlankActivity.class);
            startActivity(toBlankPage);
        });
        binding.wordBook.setOnClickListener(v -> {
            Intent toWordBookPage = new Intent();
            toWordBookPage.setClass(getActivity(), WordBookActivity.class);
            startActivity(toWordBookPage);
        });
    }

}
