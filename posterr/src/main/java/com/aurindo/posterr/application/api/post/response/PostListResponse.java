package com.aurindo.posterr.application.api.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponse {

    private List<PostResponse> postResponseList;

}
