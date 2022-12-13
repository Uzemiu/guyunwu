package com.example.guyunwu.api.resp;

import lombok.Data;

import java.util.List;

@Data
public class TodayScheduleResp {

    private Integer learn;

    private Integer review;

    private List<Word> words;
}
