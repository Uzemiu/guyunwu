package com.example.guyunwu.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.R;
import com.example.guyunwu.databinding.FragmentUserBinding;
import com.example.guyunwu.ui.user.profile.ProfileActivity;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.layout_user).setOnClickListener(v -> {
            Intent toProfilePage = new Intent();
            toProfilePage.setClass(getActivity(), ProfileActivity.class);
            startActivity(toProfilePage);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
