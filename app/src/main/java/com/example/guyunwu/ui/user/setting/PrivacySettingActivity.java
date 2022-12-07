package com.example.guyunwu.ui.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.ui.user.profile.ProfileActivity;

public class PrivacySettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting);
        initActionBar();
        findViewById(R.id.layout_profile).setOnClickListener(v -> {
            Intent toProfilePage = new Intent();
            toProfilePage.setClass(this, ProfileActivity.class);
            startActivity(toProfilePage);
        });
        findViewById(R.id.layout_system_manage).setOnClickListener(v -> {
            Intent toSystemPage = new Intent();
            toSystemPage.setClass(this, SystemManageActivity.class);
            startActivity(toSystemPage);
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
            bar.setTitle("隐私设置");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
