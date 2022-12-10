package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.databinding.FragmentLearnFinishBinding;
import com.example.guyunwu.ui.home.signIn.SignInActivity;

public class LearnFinishFragment extends Fragment {

    private FragmentLearnFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLearnFinishBinding.inflate(inflater, container, false);
        binding.btnSignIn.setOnClickListener(v -> {
            Intent toSignInPage = new Intent();
            toSignInPage.setClass(getActivity(), SignInActivity.class);
            startActivity(toSignInPage);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
