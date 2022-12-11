package com.example.guyunwu.api.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {

    private String username;

    private String avatar;

    private Integer gender;

    private Date birthDate;

}
