package com.example.guyunwu.ui.user.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initActionBar();
        findViewById(R.id.layout_birthday).setOnClickListener((v) -> {
            DatePickerDialog pickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {

                    }, 2018, 11, 11);
            pickerDialog.show();
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
            bar.setTitle("个人资料");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

