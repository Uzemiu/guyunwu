package com.example.guyunwu.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.MainActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.ScheduleRequest;
import com.example.guyunwu.api.UserRequest;
import com.example.guyunwu.api.req.LoginReq;
import com.example.guyunwu.api.resp.LoginResp;
import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.repository.WordRepository;
import com.example.guyunwu.util.SharedPreferencesUtil;
import org.jetbrains.annotations.NotNull;
import org.xutils.common.util.MD5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActionBar();
        initBinding();
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
            bar.setTitle("登录");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initBinding() {
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            Intent toRegisterPage = new Intent();
            toRegisterPage.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(toRegisterPage);
        });
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            String phoneNumber = ((EditText) findViewById(R.id.txt_name)).getText().toString();
            String password = ((EditText) findViewById(R.id.txt_pwd)).getText().toString();
            if (phoneNumber.length() == 0) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            Pattern regex = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");
            Matcher matcher = regex.matcher(phoneNumber);
            if (!matcher.matches()) {
                Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() == 0) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(this, "密码长度必须大于等于6位", Toast.LENGTH_SHORT).show();
                return;
            }
            LoginRequest(phoneNumber, password);
        });
    }

    private void LoginRequest(String phoneNumber, String password) {
        UserRequest userRequest = RequestModule.USER_REQUEST;
        ScheduleRequest scheduleRequest = RequestModule.SCHEDULE_REQUEST;

        LoginReq loginReq = new LoginReq();
        loginReq.setPhoneNumber(phoneNumber);
        loginReq.setPassword(MD5.md5(password));

        userRequest.login(loginReq).enqueue(new Callback<BaseResponse<LoginResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginResp>> call, Response<BaseResponse<LoginResp>> response) {
                BaseResponse<LoginResp> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("登录失败"));
                } else {
                    SharedPreferencesUtil.putString("token", body.getData().getToken());
                    scheduleRequest.currentSchedule().enqueue(new Callback<BaseResponse<WordResp>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<WordResp>> call, Response<BaseResponse<WordResp>> response) {
                            BaseResponse<WordResp> body1 = response.body();
                            if (body1 == null || body1.getCode() != 200) {
                                onFailure(call, new Throwable("登录失败"));
                            } else {
                                SharedPreferencesUtil.putString("phoneNumber", body.getData().getPhoneNumber());
                                SharedPreferencesUtil.putString("userName", body.getData().getUsername());
                                SharedPreferencesUtil.putString("avatar", body.getData().getAvatar());
                                SharedPreferencesUtil.putLong("birthDate", body.getData().getBirthDate() == null ? 0 : body.getData().getBirthDate().getTime());
                                SharedPreferencesUtil.putInt("gender", body.getData().getGender());

                                WordRepository wordRepository = new WordRepository();
                                WordResp wordResp = body1.getData();
                                if (wordResp == null) {
                                    // do nothing
                                } else {
                                    SharedPreferencesUtil.putLong("scheduleId", wordResp.getId());
                                    SharedPreferencesUtil.putLong("bookId", wordResp.getBookId());
                                    SharedPreferencesUtil.putInt("wordsPerDay", wordResp.getWordsPerDay());
                                    wordRepository.save(wordResp.getWords());
                                }

                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent toMainPage = new Intent();
                                toMainPage.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(toMainPage);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<WordResp>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onFailure: ", t);
                            SharedPreferencesUtil.remove("token");
                        }
                    });


                }
            }

            @Override
            public void onFailure(@NotNull Call<BaseResponse<LoginResp>> call, @NotNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }
}

