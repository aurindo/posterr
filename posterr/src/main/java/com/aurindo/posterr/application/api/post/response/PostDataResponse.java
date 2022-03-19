package com.aurindo.posterr.application.api.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@Getter
@Builder
public class PostDataResponse extends RepresentationModel<PostDataResponse> {

    private String userId;
    private Long total;

}
