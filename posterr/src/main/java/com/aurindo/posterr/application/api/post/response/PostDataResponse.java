package com.aurindo.posterr.application.api.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
public class PostDataResponse {

    private String userId;
    private Long total;

}
