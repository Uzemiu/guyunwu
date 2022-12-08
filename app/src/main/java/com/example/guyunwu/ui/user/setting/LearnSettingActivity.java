package com.example.guyunwu.ui.user.setting;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.entity.SettingEntity;
import com.example.guyunwu.entity.SettingEnum;
import com.example.guyunwu.repository.SettingRepository;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class LearnSettingActivity extends AppCompatActivity {


    private SettingEntity hasParaphrase;

    private SettingEntity hasTranslation;

    private SettingEntity hasTone;

    private final SettingRepository settingRepository = new SettingRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_setting);
        initActionBar();
        loadSettings();
        findViewById(R.id.add_paraphrase_switch).setOnClickListener(v -> {
            hasParaphrase.setBooleanData(!hasParaphrase.getBooleanData());
            settingRepository.update(hasParaphrase);
        });
        findViewById(R.id.add_translation_switch).setOnClickListener(v -> {
            hasTranslation.setBooleanData(!hasTranslation.getBooleanData());
            settingRepository.update(hasTranslation);
        });
        findViewById(R.id.add_tone_switch).setOnClickListener(v -> {
            hasTone.setBooleanData(!hasTone.getBooleanData());
            settingRepository.update(hasTone);
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
        hasParaphrase = settingRepository.findById(SettingEnum.HAS_PARAPHRASE.ordinal());
        hasTranslation = settingRepository.findById(SettingEnum.HAS_TRANSLATION.ordinal());
        hasTone = settingRepository.findById(SettingEnum.HAS_TONE.ordinal());

        SwitchMaterial switchParaphrase = findViewById(R.id.add_paraphrase_switch);
        switchParaphrase.setChecked(hasParaphrase.getBooleanData());
        SwitchMaterial switchTranslation = findViewById(R.id.add_translation_switch);
        switchTranslation.setChecked(hasTranslation.getBooleanData());
        SwitchMaterial switchTone = findViewById(R.id.add_tone_switch);
        switchTone.setChecked(hasTone.getBooleanData());
    }
}
