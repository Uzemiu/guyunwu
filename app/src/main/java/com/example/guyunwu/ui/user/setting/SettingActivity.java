package com.example.guyunwu.ui.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.MainActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.repository.WordRepository;
import com.example.guyunwu.ui.init.LoginActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initActionBar();
        findViewById(R.id.layout_privacy_setting).setOnClickListener((v) -> {
            Intent toPrivacySettingPage = new Intent();
            toPrivacySettingPage.setClass(this, PrivacySettingActivity.class);
            startActivity(toPrivacySettingPage);
        });
        findViewById(R.id.layout_learn_setting).setOnClickListener((v) -> {
            Intent toLearnSettingPage = new Intent();
            toLearnSettingPage.setClass(this, LearnSettingActivity.class);
            startActivity(toLearnSettingPage);
        });
        findViewById(R.id.layout_learn_notification).setOnClickListener((v) -> {
            Intent toPrivacySettingPage = new Intent();
            toPrivacySettingPage.setClass(this, LearnNotificationActivity.class);
            startActivity(toPrivacySettingPage);
        });
        findViewById(R.id.layout_about_us).setOnClickListener(v -> {
            Intent toAboutUsPage = new Intent();
            toAboutUsPage.setClass(this, AboutUsActivity.class);
            startActivity(toAboutUsPage);
        });
        findViewById(R.id.btn_logout).setOnClickListener((v) -> {
            SharedPreferencesUtil.remove("token");
            SharedPreferencesUtil.remove("avatar");
            SharedPreferencesUtil.remove("userName");
            SharedPreferencesUtil.remove("token");
            SharedPreferencesUtil.remove("birthDate");
            SharedPreferencesUtil.remove("gender");
            SharedPreferencesUtil.remove("phoneNumber");
            SharedPreferencesUtil.remove("scheduleId");
            SharedPreferencesUtil.remove("bookId");
            SharedPreferencesUtil.remove("wordsPerDay");
            WordRepository wordRepository = new WordRepository();
            wordRepository.delete(wordRepository.findAll());
            Toast.makeText(this, "退出登录成功", Toast.LENGTH_LONG).show();
            Intent toLoginPage = new Intent();
            toLoginPage.setClass(this, LoginActivity.class);
            startActivity(toLoginPage);
            finish();
            MainActivity.instance.finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //返回键的id
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("设置");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
