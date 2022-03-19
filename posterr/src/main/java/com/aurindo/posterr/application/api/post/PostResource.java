package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostListResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/post")
public interface PostResource {

    @GetMapping(
            value = "/{postId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostResponse> getById(
            @PathVariable(value = "postId", required = true) String postId);

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostDataResponse> fetchData(
            @PathVariable(value = "userId", required = true) String userId);

    @GetMapping(
            value = "/from-all/{limit}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostListResponse> fetchPostsFromAll(
            @PathVariable(value = "limit", required = true) Integer limit);

}
