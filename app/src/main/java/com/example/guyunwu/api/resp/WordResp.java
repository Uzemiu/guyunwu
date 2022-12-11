package com.example.guyunwu.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordResp {

    private String keyWord;

    private int keyIndex;

    private String content;

    private String translation;

    private String bookName;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private int correctAnswer;
}
