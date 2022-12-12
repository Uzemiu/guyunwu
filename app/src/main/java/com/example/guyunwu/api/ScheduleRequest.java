package com.example.guyunwu.api;

import com.example.guyunwu.api.req.LoginReq;
import com.example.guyunwu.api.req.ScheduleReq;
import com.example.guyunwu.api.resp.WordResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;

public interface ScheduleRequest {

    @POST("/schedule/add")
    Call<BaseResponse<List<WordResp>>> addSchedule(@Body ScheduleReq scheduleReq);

    @PUT("/schedule/reset")
    Call<BaseResponse<Object>> resetSchedule();

    @POST("/schedule/switch")
    Call<BaseResponse<Object>> switchSchedule(@Body ScheduleReq scheduleReq);

    @PUT("/schedule/update")
    Call<BaseResponse<Object>> updateSchedule();
}
