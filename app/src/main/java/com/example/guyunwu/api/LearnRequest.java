package com.example.guyunwu.api;

import com.example.guyunwu.api.req.DateReq;
import com.example.guyunwu.api.resp.TodayScheduleResp;
import com.example.guyunwu.api.resp.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LearnRequest {

    @GET("/learn/todaySchedule")
    Call<BaseResponse<TodayScheduleResp>> todaySchedule();

    @GET("/learn/todayLearned")
    Call<BaseResponse<Integer>> todayLearned();

    @POST("/learn/learnRecord")
    Call<BaseResponse<List<Word>>> learnRecord(@Body DateReq dateReq);
}
