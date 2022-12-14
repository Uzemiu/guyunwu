package com.example.guyunwu.api.resp;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResp {

    private String username;

    private String avatar;

    private Integer gender;

    private Date birthDate;

    private String token;

    private String phoneNumber;

}
