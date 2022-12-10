package com.example.guyunwu.apitest;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private T result;

}
