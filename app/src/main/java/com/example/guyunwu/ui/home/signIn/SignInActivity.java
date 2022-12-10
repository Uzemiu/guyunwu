package com.example.guyunwu.ui.home.signIn;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import org.xutils.x;

import java.time.LocalDateTime;

public class SignInActivity extends AppCompatActivity {

    private String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initActionBar();
        initDate();
    }

    private void initDate() {
        imageUrl = "https://bing.com/th?id=OHR.TangleCreekFalls_ZH-CN4281148652_1920x1080.jpg&qlt=100";
        LocalDateTime now = LocalDateTime.now();
        ((TextView) findViewById(R.id.daily_day)).setText(String.valueOf(now.getDayOfMonth()));
        ((TextView) findViewById(R.id.daily_year_month)).setText(now.getYear() + "年" + now.getMonthValue() + "月");
        x.image().bind(findViewById(R.id.daily_image), imageUrl);
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
            bar.setTitle("打卡签到");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
