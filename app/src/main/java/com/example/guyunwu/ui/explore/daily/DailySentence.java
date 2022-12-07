package com.example.guyunwu.ui.explore.daily;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySentence {

    private Integer id;

    private String sentence;

    private String from;

    private LocalDateTime date;

    private String imageUrl;

}
