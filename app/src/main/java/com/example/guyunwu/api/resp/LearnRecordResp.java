package com.example.guyunwu.api.resp;

import lombok.Data;

import java.util.List;

@Data
public class LearnRecordResp {

    private Boolean isClocked;

    private List<WordWithBook> words;

}
