package com.example.guyunwu.apitest;

import com.example.guyunwu.apitest.req.LoginReq;
import com.example.guyunwu.apitest.resp.MeResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserRequest {

    @GET("auth/me")
    Call<BaseResponse<MeResp>> me();

    @POST("auth/login")
    Call<BaseResponse<Object>> login(@Body LoginReq loginReq);

}
