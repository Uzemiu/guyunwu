package com.example.guyunwu.api;

import com.example.guyunwu.api.req.ScheduleReq;
import com.example.guyunwu.api.req.UpdateScheduleReq;
import com.example.guyunwu.api.resp.ScheduleResp;
import com.example.guyunwu.api.resp.SimpleScheduleResp;
import retrofit2.Call;
import retrofit2.http.*;

public interface ScheduleRequest {

    @POST("/schedule/add")
    Call<BaseResponse<Object>> addSchedule(@Body ScheduleReq scheduleReq);

    @GET("/schedule/currentSchedule")
    Call<BaseResponse<SimpleScheduleResp>> currentSchedule();

    @PUT("/schedule/reset")
    Call<BaseResponse<SimpleScheduleResp>> resetSchedule();

    @POST("/schedule/switch")
        // return all about this schedule
    Call<BaseResponse<SimpleScheduleResp>> switchSchedule(@Body ScheduleReq scheduleReq);

    @PUT("/schedule/update")
    Call<BaseResponse<Object>> updateSchedule(@Body UpdateScheduleReq updateScheduleReq);

    @GET("/schedule/progress/{scheduleId}")
    Call<BaseResponse<ScheduleResp>> getSchedule(@Path("scheduleId") Long scheduleId);
}
