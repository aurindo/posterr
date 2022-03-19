package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostListResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class PostController implements PostResource {

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity<PostResponse> getById(String postId) {
        Post post = postService.getById(postId);
        PostResponse postResponse = PostResponse.factory(post);

        postResponse.add(linkTo(methodOn(PostController.class).getById(postId)).withSelfRel());

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDataResponse> fetchData(String userId) {
        Long numberPosts = postService.numberPostsFromUser(userId);
        PostDataResponse postDataResponse = PostDataResponse.builder().userId(userId).total(numberPosts).build();

        postDataResponse.add(linkTo(methodOn(PostController.class).fetchData(userId)).withSelfRel());

        return new ResponseEntity<>(postDataResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostListResponse> fetchPostsFromAll(Integer limit) {

        List<PostResponse> postResponseList = postService.fetchPostsFromAll(limit).stream().map(
                post -> PostResponse.factory(post)).collect(Collectors.toList());

        postResponseList.stream().forEach(postResponse -> {
            Link selfLink = linkTo(methodOn(PostController.class)
                    .getById(postResponse.getPostId())).withSelfRel();
            postResponse.add(selfLink);
        });

        PostListResponse postListResponse = PostListResponse.builder().postResponseList(postResponseList).build();
        postListResponse.add(linkTo(methodOn(PostController.class)
                .fetchPostsFromAll(limit)).withSelfRel());

        return new ResponseEntity<>(postListResponse, HttpStatus.OK);
    }
}
