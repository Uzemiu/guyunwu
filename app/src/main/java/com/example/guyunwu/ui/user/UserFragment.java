package com.example.guyunwu.ui.user;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.databinding.FragmentUserBinding;
import com.example.guyunwu.ui.home.wordbook.WordBookActivity;
import com.example.guyunwu.ui.init.LoginActivity;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.ui.user.profile.ProfileActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;
import com.google.android.material.switchmaterial.SwitchMaterial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    private boolean darkMode;

    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SwitchMaterial switchMaterial = root.findViewById(R.id.dark_mode_switch);
        darkMode = SharedPreferencesUtil.getBoolean("darkMode", false);
        switchMaterial.setChecked(darkMode);
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        initRouter(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initUser();
    }

    private void initUser() {
        String avatar = SharedPreferencesUtil.getString("avatar", null);
        if (avatar != null) {
            Glide.with(this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(binding.avatar);
        } else {
            Glide.with(this).load(R.drawable.ic_user_user_24dp).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(binding.avatar);
        }
        String userName = SharedPreferencesUtil.getString("userName", "未登录");
        binding.username.setText(userName);

        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;

        learnRequest.clockDays().enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                BaseResponse<Integer> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("获取失败"));
                } else {
                    if (binding != null) {
                        binding.dayNum.setText(body.getData() + " ");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() == null ? "请求失败" : t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

        learnRequest.totalLearnedWords().enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                BaseResponse<Integer> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("获取失败"));
                } else {
                    if (binding != null) {
                        binding.articleNum.setText(body.getData() + " ");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() == null ? "请求失败" : t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
        binding.layoutMyBook.setOnClickListener(v -> {
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
            darkMode = !darkMode;
            SharedPreferencesUtil.putBoolean("darkMode", darkMode);
            int currentNightMode = root.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        binding.layoutMyStar.setOnClickListener(v -> {
            Intent toWordBook = new Intent();
            toWordBook.setClass(getActivity(), WordBookActivity.class);
            startActivity(toWordBook);
        });
    }
}
