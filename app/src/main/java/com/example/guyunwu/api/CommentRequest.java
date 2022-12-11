package com.example.guyunwu.api;

import com.example.guyunwu.api.req.AddCommentReq;
import com.example.guyunwu.ui.explore.comment.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentRequest {

    @GET("comment/list")
    Call<BaseResponse<List<Comment>>> listComment(@Query("articleId") Long articleId, @Query("page") int page, @Query("size") int size);

    @POST("comment")
    Call<BaseResponse<Comment>> addComment(@Body AddCommentReq req);

    @POST("comment/like/{id}")
    Call<BaseResponse<Object>> doLikeComment(@Path("id") Long id);

}
