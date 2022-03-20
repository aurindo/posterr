package com.aurindo.posterr.application.api.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CreatePostRequest {

    private String userId;
    private String content;
    private String type;
    private String parentId;

}
