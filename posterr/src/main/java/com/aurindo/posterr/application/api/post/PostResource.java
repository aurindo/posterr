package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.request.CreatePostRequest;
import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/post")
public interface PostResource {

    @GetMapping(
            value = "/{postId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostResponse> getById(
            @PathVariable(value = "postId") String postId);

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostDataResponse> fetchData(
            @PathVariable(value = "userId") String userId);

    @GetMapping(
            value = "/from-all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity fetchPostsFromAll(
            Pageable pageable);

    @GetMapping(
            value = "/from-user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity fetchPostsFromUser(
            @RequestParam(value = "userId") String userId,
            Pageable pageable);

    @GetMapping(
            value = "/from-user-home",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity fetchPostsFromUserHome(
            @RequestParam(value = "userId") String userId,
            Pageable pageable);

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<PostResponse> createPosts(
            @RequestBody CreatePostRequest createPostRequest);
}
