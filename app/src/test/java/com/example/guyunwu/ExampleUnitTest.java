package com.example.guyunwu;

import com.example.guyunwu.apitest.BaseResponse;
import com.example.guyunwu.apitest.RequestModule;
import com.example.guyunwu.apitest.UserRequest;
import com.example.guyunwu.apitest.req.LoginReq;
import com.example.guyunwu.util.SharedPreferencesUtil;

import org.junit.Before;
import org.junit.Test;
import org.xutils.common.util.MD5;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Before
    public void setup(){
        SharedPreferencesUtil.setSharedPreferences(null);
    }

    @Test
    public void addition_isCorrect() throws Exception {

        UserRequest userRequest = RequestModule.USER_REQUEST;

        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("ecnu_admin");
        loginReq.setPassword(MD5.md5("pl,okm123"));
        BaseResponse<?> r = userRequest.login(loginReq).execute().body();
        int i = 0;
    }

}
