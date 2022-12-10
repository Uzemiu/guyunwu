package com.example.guyunwu.ui.explore.daily;

import com.alibaba.fastjson2.annotation.JSONField;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySentence {

    private Integer id;

    private String content;

    private String source;

    private String author;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String imageUrl;

}
