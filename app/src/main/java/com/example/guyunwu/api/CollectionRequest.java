package com.example.guyunwu.api;

import com.example.guyunwu.api.resp.SimpleScheduleResp;
import com.example.guyunwu.api.resp.WordWithBook;
import com.example.guyunwu.ui.explore.daily.DailySentence;
import com.example.guyunwu.ui.user.book.Book;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CollectionRequest {

    @GET("/collection/book/all")
    Call<BaseResponse<List<Book>>> allBook();

    @GET("/collection/book/my")
    Call<BaseResponse<List<Book>>> myBook();

    @GET("/collection/dailySentence")
    Call<BaseResponse<List<DailySentence>>> dailySentences(@Query("page") int page, @Query("size") int size);

    @DELETE("/collection/word/cancel/{wordId}")
    Call<BaseResponse<Object>> cancelWord(@Path("wordId") String wordId);

    @PUT("/collection/word/isCollected/{wordId}")
    Call<BaseResponse<Boolean>> isCollected(@Path("wordId") String wordId);

    @GET("/collection/word/my")
    Call<BaseResponse<List<WordWithBook>>> myWord();

    @POST("/collection/word/{wordId}")
    Call<BaseResponse<Object>> starWord(@Path("wordId") String wordId);

    @GET("/collection/hasBook/{bookId}")
    Call<BaseResponse<Boolean>> hasBook(@Path("bookId") Long bookId);
}
