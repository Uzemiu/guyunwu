package com.example.guyunwu.api;

import com.example.guyunwu.api.req.LoginReq;
import com.example.guyunwu.api.resp.MeResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserRequest {

    @POST("user/login")
    Call<BaseResponse<MeResp>> login(@Body LoginReq loginReq);

    @POST("user/register")
    Call<BaseResponse<Object>> register(@Body LoginReq loginReq);

    @PUT("user/update")
    Call<BaseResponse<MeResp>> update();


}
