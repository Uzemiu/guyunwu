package com.example.guyunwu.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordResp {

    private List<Word> words;

    private Long id;

    private Long bookId;

    private Integer wordsPerDay;

}
