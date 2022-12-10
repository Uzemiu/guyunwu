package com.example.guyunwu;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.guyunwu.apitest.BaseResponse;
import com.example.guyunwu.apitest.RequestModule;
import com.example.guyunwu.apitest.UserRequest;
import com.example.guyunwu.apitest.req.LoginReq;
import com.example.guyunwu.apitest.resp.MeResp;
import com.example.guyunwu.util.SharedPreferencesUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xutils.common.util.MD5;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleRequestTest {

    @Before
    public void setup(){
        SharedPreferences sharedPreferences = InstrumentationRegistry.getInstrumentation()
                .getContext().getSharedPreferences("guyunwutest", Context.MODE_PRIVATE);
        SharedPreferencesUtil.setSharedPreferences(sharedPreferences);
    }

    @Test
    public void useAppContext() throws IOException {
        UserRequest userRequest = RequestModule.USER_REQUEST;

        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("ecnu_admin");
        loginReq.setPassword(MD5.md5("pl,okm123"));
        BaseResponse<?> r = userRequest.login(loginReq).execute().body();
        assertNotNull(r);
        assertEquals(Integer.valueOf(0), r.getCode());

        BaseResponse<MeResp> meResp = userRequest.me().execute().body();
        assertNotNull(meResp);
        assertEquals("ecnu_admin", meResp.getResult().getUsername());
    }

    @Test
    public void async(){
        UserRequest userRequest = RequestModule.USER_REQUEST;

        try {
            System.out.println(userRequest.me().execute().body());
        } catch (IOException e) {
//            Toast.makeText(InstrumentationRegistry.getInstrumentation().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        userRequest.me().enqueue(new Callback<BaseResponse<MeResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<MeResp>> call, Response<BaseResponse<MeResp>> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponse<MeResp>> call, Throwable t) {
//                Toast.makeText(InstrumentationRegistry.getInstrumentation().getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
