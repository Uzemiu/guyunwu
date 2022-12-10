package com.example.guyunwu.api;

import com.example.guyunwu.api.req.PageReq;
import com.example.guyunwu.ui.explore.daily.DailySentence;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CollectionRequest {

    @GET("/collection/dailySentence")
    Call<BaseResponse<List<DailySentence>>> dailySentences(@Query("page") int page, @Query("size") int size);
}
