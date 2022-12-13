package com.example.guyunwu.api.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateReq {

    private Integer year;

    private Integer month;

    private Integer day;
}
