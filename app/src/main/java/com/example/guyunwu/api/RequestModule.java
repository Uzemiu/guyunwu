package com.example.guyunwu.api;

import com.alibaba.fastjson2.JSON;
import com.example.guyunwu.exception.BadRequestException;
import com.example.guyunwu.util.SharedPreferencesUtil;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class RequestModule {

    private static final OkHttpClient HTTP_CLIENT;

    private static final Retrofit RETROFIT;

    public static final String BASE_URL = "http://10.0.2.2:8080/";

//    public static final String BASE_URL = "http://192.168.31.62:8080/";

    public static final UserRequest USER_REQUEST;

    public static final CollectionRequest COLLECTION_REQUEST;

    public static final ArticleRequest ARTICLE_REQUEST;

    static {
        HTTP_CLIENT = new OkHttpClient.Builder()
                // add cookie
                .addInterceptor(chain -> {
                    String token = SharedPreferencesUtil.getString("token", "");
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                })
//                 error handler
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    if(response.code() >= 400){
                        String errorString = "网络请求失败";
                        switch (response.code()){
                            case 401:
                                errorString = "未登录";
                                break;
                            case 403:
                                errorString = "你没有权限这么做";
                                break;
                            default:
                        }
                        ResponseBody body = response.body();
                        if(body != null){
                            try {
                               BaseResponse<?> r =  JSON.parseObject(body.string(), BaseResponse.class);
                                errorString = r.getMessage();
                            } catch (Exception ignore){}
                        }
                        throw new BadRequestException(errorString);
                    }
                    return response;
                })
                .build();

        RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        USER_REQUEST = RETROFIT.create(UserRequest.class);
        COLLECTION_REQUEST = RETROFIT.create(CollectionRequest.class);
        ARTICLE_REQUEST = RETROFIT.create(ArticleRequest.class);
    }
}
