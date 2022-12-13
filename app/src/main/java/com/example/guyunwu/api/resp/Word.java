package com.example.guyunwu.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private Long wordId;

    private Integer bookId;

    private String keyWord;

    private Integer keyIndex;

    private String content;

    private String translate;

    private String bookName;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private Integer correctAnswer;

    private Integer status;
}
