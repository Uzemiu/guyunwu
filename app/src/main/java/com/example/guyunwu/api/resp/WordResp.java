package com.example.guyunwu.api.resp;

import com.example.guyunwu.ui.user.book.Book;
import lombok.Data;

import java.util.List;

@Data
public class WordResp {

    private Book book;

    private List<Word> words;
}
