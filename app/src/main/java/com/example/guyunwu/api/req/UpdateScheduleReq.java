package com.example.guyunwu.api.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleReq {

    private Long id;

    private Integer wordsPerDay;
}
