package com.example.guyunwu.ui.user.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    private Integer id;

    private String coverImage;

    private String name;

    private Author author;

    private String introduce;

    private String press;

    private Long reads;

    private Long likes;

    private String category;
}
