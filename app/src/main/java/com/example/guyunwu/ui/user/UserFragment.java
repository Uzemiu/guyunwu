package com.example.guyunwu.ui.user;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.R;
import com.example.guyunwu.databinding.FragmentUserBinding;
import com.example.guyunwu.entity.SettingEntity;
import com.example.guyunwu.entity.SettingEnum;
import com.example.guyunwu.repository.SettingRepository;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.ui.user.profile.ProfileActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class UserFragment extends Fragment {

    private SettingEntity darkMode;

    private final SettingRepository settingRepository = new SettingRepository();
    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SwitchMaterial switchMaterial = root.findViewById(R.id.dark_mode_switch);
        darkMode = settingRepository.findById(SettingEnum.DARK_MODE.ordinal());
        Boolean booleanData = darkMode.getBooleanData();
        switchMaterial.setChecked(booleanData);
        if (booleanData) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        initRouter(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRouter(View root) {
        binding.layoutUser.setOnClickListener(v -> {
            Intent toProfilePage = new Intent();
            toProfilePage.setClass(getActivity(), ProfileActivity.class);
            startActivity(toProfilePage);
        });
        binding.textMyBook.setOnClickListener(v -> {
            Intent toMyBook = new Intent();
            toMyBook.setClass(getActivity(), MyBookActivity.class);
            startActivity(toMyBook);
        });
        binding.layoutHelp.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/Uzemiu/guyunwu/issues");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        binding.darkModeSwitch.setOnClickListener(v -> {
            darkMode.setBooleanData(!darkMode.getBooleanData());
            settingRepository.update(darkMode);
            int currentNightMode = root.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }
}
