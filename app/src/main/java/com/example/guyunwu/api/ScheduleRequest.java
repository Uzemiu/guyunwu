package com.example.guyunwu.api;

import com.example.guyunwu.api.req.LoginReq;
import com.example.guyunwu.api.req.ScheduleReq;
import com.example.guyunwu.api.req.UpdateScheduleReq;
import com.example.guyunwu.api.resp.ScheduleResp;
import com.example.guyunwu.api.resp.WordResp;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ScheduleRequest {

    @POST("/schedule/add")
    Call<BaseResponse<Object>> addSchedule(@Body ScheduleReq scheduleReq);

    @GET("/schedule/currentSchedule")
    Call<BaseResponse<WordResp>> currentSchedule();

    @PUT("/schedule/reset")
    Call<BaseResponse<WordResp>> resetSchedule();

    @POST("/schedule/switch")
    // return all about this schedule
    Call<BaseResponse<WordResp>> switchSchedule(@Body ScheduleReq scheduleReq);

    @PUT("/schedule/update")
    Call<BaseResponse<Object>> updateSchedule(@Body UpdateScheduleReq updateScheduleReq);

    @GET("/schedule/progress/{scheduleId}")
    Call<BaseResponse<ScheduleResp>> getSchedule(@Path("scheduleId") Long scheduleId);
}
