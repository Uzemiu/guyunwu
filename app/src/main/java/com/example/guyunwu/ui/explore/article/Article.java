package com.example.guyunwu.ui.explore.article;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article implements Serializable {

    @Column(name = "id", isId = true)
    private Integer id;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private Author author;

    @Column(name = "content")
    private String content;

    @Column(name = "summary")
    private String summary;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "reads")
    private Long reads;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "category")
    private String category;

    @Column(name = "tags")
    private List<String> tags;
}
