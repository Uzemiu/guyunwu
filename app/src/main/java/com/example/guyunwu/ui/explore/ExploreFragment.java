package com.example.guyunwu.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.FragmentExploreBinding;
import com.example.guyunwu.ui.explore.daily.DailySentenceActivity;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;

    private void init(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel exploreViewModel =
                new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        exploreViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        root.findViewById(R.id.explore_to_daily_page).setOnClickListener(v -> {
            Intent toDailyPage = new Intent();
            toDailyPage.setClass(getActivity(), DailySentenceActivity.class);
            startActivity(toDailyPage);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
