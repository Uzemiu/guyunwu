package com.example.guyunwu.ui.user.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;

public class SystemManageActivity extends AppCompatActivity {

    private final View.OnClickListener onClickListener = v -> {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_manage);
        initActionBar();
        findViewById(R.id.layout_network_permission).setOnClickListener(onClickListener);
        findViewById(R.id.layout_photo_permission).setOnClickListener(onClickListener);
        findViewById(R.id.layout_location_permission).setOnClickListener(onClickListener);
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
            bar.setTitle("系统权限管理");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


}
