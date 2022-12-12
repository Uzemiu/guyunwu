package com.example.guyunwu.ui.user.book;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book implements Serializable {
    @Column(name = "id", isId = true)
    private Integer id;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private Author author;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "content")
    private String content;

    @Column(name = "press")
    private String press;

    @Column(name = "reads")
    private Long reads;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "category")
    private String category;
}
