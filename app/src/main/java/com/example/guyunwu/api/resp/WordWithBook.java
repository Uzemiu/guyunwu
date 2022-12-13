package com.example.guyunwu.api.resp;

import com.example.guyunwu.ui.user.book.Book;
import lombok.Data;

import java.io.Serializable;

@Data
public class WordWithBook implements Serializable {

    private Long wordId;

    private Book book;

    private String keyWord;

    private Integer keyIndex;

    private String content;

    private String translate;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private Integer correctAnswer;

    private Integer status;
}
