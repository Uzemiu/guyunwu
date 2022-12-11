package com.example.guyunwu.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.UserRequest;
import com.example.guyunwu.api.req.LoginReq;
import org.jetbrains.annotations.NotNull;
import org.xutils.common.util.MD5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            bar.setTitle("注册");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initBinding() {
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            String phoneNumber = ((EditText) findViewById(R.id.txt_name)).getText().toString();
            String password = ((EditText) findViewById(R.id.txt_pwd)).getText().toString();
            String passwordRepeat = ((EditText) findViewById(R.id.txt_pwd_repeat)).getText().toString();
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
            if (!password.equals(passwordRepeat)) {
                Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                return;
            }
            registerRequest(phoneNumber, password);
        });
    }

    private void registerRequest(String phoneNumber, String password) {
        UserRequest userRequest = RequestModule.USER_REQUEST;

        LoginReq loginReq = new LoginReq();
        loginReq.setPhoneNumber(phoneNumber);
        loginReq.setPassword(MD5.md5(password));

        userRequest.register(loginReq).enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.body() == null || response.body().getCode() != 200) {
                    onFailure(call, new Throwable("注册失败"));
                    return;
                }
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent toLoginPage = new Intent();
                toLoginPage.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(toLoginPage);
            }

            @Override
            public void onFailure(@NotNull Call<BaseResponse<Object>> call, @NotNull Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }
}
