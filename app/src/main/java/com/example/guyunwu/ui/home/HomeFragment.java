package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.example.guyunwu.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.guyunwu.databinding.FragmentHomeBinding;
import com.example.guyunwu.ui.home.schedule.UpdateScheduleActivity;
import com.example.guyunwu.ui.home.signIn.SignInActivity;
import com.example.guyunwu.ui.user.profile.ProfileActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.book_image).setOnClickListener(v -> {
//            Intent toProfilePage = new Intent();
//            toProfilePage.setClass(getActivity(), ProfileActivity.class);
//            startActivity(toProfilePage);
        });

        root.findViewById(R.id.schedule).setOnClickListener(v -> {
            Intent toSchedulePage = new Intent();
            toSchedulePage.setClass(getActivity(), UpdateScheduleActivity.class);
            startActivity(toSchedulePage);
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
