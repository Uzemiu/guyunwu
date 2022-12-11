package com.example.guyunwu.ui.user.setting;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.util.SharedPreferencesUtil;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class LearnSettingActivity extends AppCompatActivity {
    private boolean hasParaphrase;

    private boolean hasTranslation;

    private boolean hasTone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_setting);
        initActionBar();
        loadSettings();
        findViewById(R.id.add_paraphrase_switch).setOnClickListener(v -> {
            hasParaphrase = !hasParaphrase;
            SharedPreferencesUtil.putBoolean("hasParaphrase", hasParaphrase);
        });
        findViewById(R.id.add_translation_switch).setOnClickListener(v -> {
            hasTranslation = !hasTranslation;
            SharedPreferencesUtil.putBoolean("hasTranslation", hasTranslation);
        });
        findViewById(R.id.add_tone_switch).setOnClickListener(v -> {
            hasTone = !hasTone;
            SharedPreferencesUtil.putBoolean("hasTone", hasTone);
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
            bar.setTitle("学习设置");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadSettings() {

        hasParaphrase = SharedPreferencesUtil.getBoolean("hasParaphrase", false);
        hasTranslation = SharedPreferencesUtil.getBoolean("hasTranslation", false);
        hasTone = SharedPreferencesUtil.getBoolean("hasTone", false);

        SwitchMaterial switchParaphrase = findViewById(R.id.add_paraphrase_switch);
        switchParaphrase.setChecked(hasParaphrase);
        SwitchMaterial switchTranslation = findViewById(R.id.add_translation_switch);
        switchTranslation.setChecked(hasTranslation);
        SwitchMaterial switchTone = findViewById(R.id.add_tone_switch);
        switchTone.setChecked(hasTone);
    }
}
