package com.example.guyunwu.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface FileUploadRequest {

    @Multipart
    @POST("uploadPic/{cid}")
    Call<BaseResponse<String>> uploadImage(@Part MultipartBody.Part part, @Path("cid")String cid);

}
