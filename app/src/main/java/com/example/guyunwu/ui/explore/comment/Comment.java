package com.example.guyunwu.ui.explore.comment;

import com.example.guyunwu.ui.explore.article.Author;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Column(name = "id", isId = true)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "author")
    private Author author;

    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;
}
