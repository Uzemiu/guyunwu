package com.example.guyunwu.api;

import com.example.guyunwu.api.resp.TodayScheduleResp;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnRequest {

    @GET("/collection/todaySchedule")
    Call<BaseResponse<TodayScheduleResp>> todaySchedule();

    @GET("/collection/todayLearned")
    Call<BaseResponse<Integer>> todayLearned();
}
