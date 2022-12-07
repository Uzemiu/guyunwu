package com.example.guyunwu.ui.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;

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
        findViewById(R.id.layout_learn_notification).setOnClickListener((v) -> {
            Intent toPrivacySettingPage = new Intent();
            toPrivacySettingPage.setClass(this, LearnNotificationActivity.class);
            startActivity(toPrivacySettingPage);
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
