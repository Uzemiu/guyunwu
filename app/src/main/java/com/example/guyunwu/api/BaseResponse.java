package com.example.guyunwu.api;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer status;

    private String message;

    private T data;

}
