package com.example.guyunwu.api;

import com.example.guyunwu.api.resp.LearnRecordResp;
import com.example.guyunwu.api.resp.TodayScheduleResp;
import com.example.guyunwu.api.resp.WordResp;
import com.example.guyunwu.api.resp.WordWithBook;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.Date;
import java.util.List;

public interface LearnRequest {

    @GET("/learn/todaySchedule")
    Call<BaseResponse<TodayScheduleResp>> todaySchedule();

    @GET("/learn/todayWords")
    Call<BaseResponse<WordResp>> todayWords();

    @POST("/learn/learnRecord")
    Call<BaseResponse<LearnRecordResp>> learnRecord(@Body Date date);

    @GET("/learn/totalLearnedWords")
    Call<BaseResponse<Integer>> totalLearnedWords();

    @GET("/learn/clockDays")
    Call<BaseResponse<Integer>> clockDays();

    @POST("/learn/learn")
    Call<BaseResponse<Object>> learn(@Body Long wordId);

    @PUT("/learn/clockIn")
    Call<BaseResponse<Object>> clockIn();
}
