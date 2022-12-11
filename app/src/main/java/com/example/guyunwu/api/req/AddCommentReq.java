package com.example.guyunwu.api.req;

import lombok.Data;

@Data
public class AddCommentReq {

    private Long articleId;

    private String content;

}
