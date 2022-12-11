package com.example.guyunwu.api;

import com.example.guyunwu.api.req.LoginReq;
import com.example.guyunwu.api.req.UserReq;
import com.example.guyunwu.api.resp.LoginResp;
import com.example.guyunwu.api.resp.UserResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserRequest {

    @POST("user/login")
    Call<BaseResponse<LoginResp>> login(@Body LoginReq loginReq);

    @POST("user/register")
    Call<BaseResponse<Object>> register(@Body LoginReq loginReq);

    @PUT("user/update")
    Call<BaseResponse<UserResp>> update(@Body UserReq userReq);

}
