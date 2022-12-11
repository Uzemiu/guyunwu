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
import com.example.guyunwu.ui.init.LoginActivity;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.ui.user.profile.ProfileActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;
import com.google.android.material.switchmaterial.SwitchMaterial;
import org.xutils.x;

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
        initUser();
        return root;
    }

    private void initUser() {
        String avatar = SharedPreferencesUtil.getString("avatar", null);
        if (avatar != null) {
            x.image().bind(binding.avatar, avatar);
        } else {
            binding.avatar.setBackgroundResource(R.drawable.ic_user_user_24dp);
        }
        String userName = SharedPreferencesUtil.getString("userName", "未登录");
        binding.username.setText(userName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRouter(View root) {
        binding.layoutUser.setOnClickListener(v -> {
            Intent toPage = new Intent();
            if (SharedPreferencesUtil.contain("token")) {
                toPage.setClass(getActivity(), ProfileActivity.class);
            } else {
                toPage.setClass(getActivity(), LoginActivity.class);
            }
            startActivity(toPage);
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
