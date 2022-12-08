package com.example.guyunwu.ui.explore.article;

import android.text.TextUtils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private Integer id;

    private String name;

    private String avatar;

    public String gertName(){
        return TextUtils.isEmpty(name) ? "匿名" : name;
    }
}
