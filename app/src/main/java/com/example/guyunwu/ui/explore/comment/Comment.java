package com.example.guyunwu.ui.explore.comment;

import com.example.guyunwu.ui.explore.article.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Integer id;

    private Integer articleId;

    private Author author;

    private String content;

    private Long likes;

    private Date publishDate;

    private Boolean liked;
}
