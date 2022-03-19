package com.aurindo.posterr.application.api.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponse extends RepresentationModel<PostListResponse> {

    private List<PostResponse> postResponseList;

}
