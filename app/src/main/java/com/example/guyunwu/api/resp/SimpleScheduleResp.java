package com.example.guyunwu.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleScheduleResp {

    private Long id;

    private Long bookId;

    private Integer wordsPerDay;

}
