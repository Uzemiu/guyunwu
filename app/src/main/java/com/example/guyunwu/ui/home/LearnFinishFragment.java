package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.databinding.FragmentLearnFinishBinding;
import com.example.guyunwu.ui.home.signIn.SignInActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearnFinishFragment extends Fragment {

    private static final String TAG = "LearnFinishFragment";
    private FragmentLearnFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLearnFinishBinding.inflate(inflater, container, false);
        initBinding();
        Bundle bundle = getArguments();
        if (bundle != null) {
            int hasLearned = bundle.getInt("hasLearned");
            binding.hasLearn.setText(hasLearned + " ");
        }
        return binding.getRoot();
    }

    private void initBinding() {
        binding.btnSignIn.setOnClickListener(v -> {
            Intent toSignInPage = new Intent();
            toSignInPage.setClass(getActivity(), SignInActivity.class);
            startActivity(toSignInPage);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
