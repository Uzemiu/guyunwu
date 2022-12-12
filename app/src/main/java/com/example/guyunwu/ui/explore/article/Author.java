package com.example.guyunwu.ui.explore.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private Integer id;

    private String username;

    private String avatar;

}
