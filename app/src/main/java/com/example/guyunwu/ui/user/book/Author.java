package com.example.guyunwu.ui.user.book;

import android.text.TextUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private Integer id;

    private String name;

    private String dynasty;

    public String gertName() {
        return TextUtils.isEmpty(name) ? "匿名" : name;
    }

    public String getDynasty() {
        return dynasty;
    }
}
