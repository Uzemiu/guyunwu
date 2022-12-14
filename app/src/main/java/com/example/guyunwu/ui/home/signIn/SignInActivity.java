package com.example.guyunwu.ui.home.signIn;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDateTime;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initActionBar();
        initDate();
        trySignIn();
    }

    private void trySignIn() {
        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;

        learnRequest.clockIn().enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                BaseResponse<Object> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("打卡失败"));
                } else {
                    Toast.makeText(SignInActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
