package com.example.guyunwu.ui.explore.daily;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
