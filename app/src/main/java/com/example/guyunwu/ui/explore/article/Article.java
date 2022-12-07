package com.example.guyunwu.ui.explore.article;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    private Integer id;

    private String coverImage;

    private String title;

    private Author author;

    private String content;

    private LocalDateTime publishDate;

    private Long reads;

    private Long likes;

    private String category;

    private List<String> tags;
}
