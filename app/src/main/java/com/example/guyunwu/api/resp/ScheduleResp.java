package com.example.guyunwu.api.resp;

import com.example.guyunwu.ui.user.book.Book;
import lombok.Data;

@Data
public class ScheduleResp {

    private Book book;

    private Integer learned;

    private Integer all;

}
