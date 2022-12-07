package com.example.guyunwu.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class ArticleEntity {

    @Column(name = "id", isId = true)
    private Integer id;

    @Column(name = "cover_image")
    private String coverImage;

    private String title;

    private String author;

    private String content;

    private String publishDate;

    private Long reads;

    private Long likes;

    private String category;

    private String tags;

}
