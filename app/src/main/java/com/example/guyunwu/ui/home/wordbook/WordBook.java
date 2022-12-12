package com.example.guyunwu.ui.home.wordbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordBook implements Serializable {

    private String keyTitle;

    private String content;

    private String correctAnswer;

    private String bookName;

    private String translate;

}
