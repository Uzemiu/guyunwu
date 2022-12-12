package com.example.guyunwu.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnRequest {

    @GET("/collection/toBeReviewed")
    Call<BaseResponse<Integer>> toBeReviewed();

    @GET("/collection/toBeLearned")
    Call<BaseResponse<Integer>> toBeLearned();
}
