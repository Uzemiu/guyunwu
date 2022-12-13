package com.example.guyunwu.api.resp;

import com.example.guyunwu.ui.user.book.Book;
import lombok.Data;

import java.util.List;

@Data
public class TodayScheduleResp {

    private Integer learn;

    private Integer review;

    private List<Word> words;

    private Book book;
}
