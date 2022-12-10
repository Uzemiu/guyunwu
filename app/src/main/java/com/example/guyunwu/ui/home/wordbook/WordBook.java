package com.example.guyunwu.ui.home.wordbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordBook implements Serializable {

    private String title;

    private String content;

    /**
     * 书名
     */
    private String reference;


    private String translation;

    /**
     * 释义
     */
    private String key;

}
