package com.example.guyunwu.api;

import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.req.AddArticleReq;
import com.example.guyunwu.ui.explore.article.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleRequest {

    @GET("/article/{id}")
    Call<BaseResponse<Article>> getArticle(@Path("id") Long id);

    @GET("/article/list")
    Call<BaseResponse<List<Article>>> articles(@Query("page") int page, @Query("size") int size, @Query("category") String category);

    @POST("/article")
    Call<BaseResponse<Object>> addArticle(@Body AddArticleReq req);

    @GET("/article/like/{id}")
    Call<BaseResponse<Boolean>> getArticleLike(@Path("id") Long id);

    @POST("/article/like/{id}")
    Call<BaseResponse<Object>> doLikeArticle(@Path("id") Long id);
}
