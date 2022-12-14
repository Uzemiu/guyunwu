package com.example.guyunwu.ui.explore.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    private Long id;

    private String coverImage;

    private String title;

    private Author author;

    private String content;

    private String summary;

    private Date publishDate;

    private Long reads;

    private Long likes;

    private String category;

    private List<String> tags;
}
