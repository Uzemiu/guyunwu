package com.example.guyunwu.api;

import com.example.guyunwu.api.req.DateReq;
import com.example.guyunwu.api.resp.TodayScheduleResp;
import com.example.guyunwu.api.resp.Word;

import java.util.List;

import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.api.resp.WordWithBook;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LearnRequest {

    @GET("/learn/todaySchedule")
    Call<BaseResponse<TodayScheduleResp>> todaySchedule();

    @GET("/learn/todayWords")
    Call<BaseResponse<WordResp>> todayWords();

    @POST("/learn/learnRecord")
    Call<BaseResponse<List<WordWithBook>>> learnRecord(@Body DateReq dateReq);
}
