package com.example.guyunwu.apitest;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer status;

    private String message;

    private T data;

}
