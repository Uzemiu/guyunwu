package com.example.guyunwu.api.req;

import lombok.Data;

@Data
public class AddArticleReq {

    private String title;

    private String content;

    private String summary;

    private String coverImage;
}
