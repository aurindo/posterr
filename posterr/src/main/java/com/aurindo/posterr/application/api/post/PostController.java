package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.request.CreatePostRequest;
import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.application.api.post.response.PostResponseModelAssembler;
import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.service.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class PostController implements PostResource {

    @Autowired
    private PostService postService;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    private final PostResponseModelAssembler postResponseModelAssembler;

    @Override
    public ResponseEntity<PostResponse> getById(String postId) {
        Post post = postService.getById(postId);
        PostResponse postResponse = PostResponse.factory(post);

        postResponse.add(linkTo(methodOn(PostController.class).getById(postId)).withSelfRel());

        return ResponseEntity.ok().body(postResponse);
    }

    @Override
    public ResponseEntity<PostDataResponse> fetchData(String userId) {
        Long numberPosts = postService.numberPostsFromUser(userId);
        PostDataResponse postDataResponse = PostDataResponse.builder().userId(userId).total(numberPosts).build();

        postDataResponse.add(linkTo(methodOn(PostController.class).fetchData(userId)).withSelfRel());

        return ResponseEntity.ok().body(postDataResponse);
    }

    @Override
    public ResponseEntity fetchPostsFromAll(Pageable pageable) {
        Page<Post> postPage = postService.fetchPostsFromAll(pageable);

        Page<PostResponse> postResponsePage = getPostResponses(pageable, postPage);

        return ResponseEntity.
                ok().
                contentType(MediaTypes.HAL_JSON).
                body(pagedResourcesAssembler.toModel(postResponsePage, postResponseModelAssembler));
    }

    @Override
    public ResponseEntity fetchPostsFromUser(String userId, Pageable pageable) {
        Page<Post> postPage = postService.fetchMyPosts(userId, pageable);

        Page<PostResponse> postResponsePage = getPostResponses(pageable, postPage);

        return ResponseEntity.
                ok().
                contentType(MediaTypes.HAL_JSON).
                body(pagedResourcesAssembler.toModel(postResponsePage, postResponseModelAssembler));
    }

    @Override
    public ResponseEntity<PostResponse> createPosts(@Valid CreatePostRequest createPostRequest) {

        Post post = postService.createPost(
                createPostRequest.getUserId(),
                createPostRequest.getContent(),
                createPostRequest.getParentId(),
                createPostRequest.getType());

        PostResponse postResponse = PostResponse.factory(post);

        postResponse.add(linkTo(methodOn(PostController.class).getById(post.getId())).withSelfRel());

        return ResponseEntity.ok().body(postResponse);
    }

    private Page<PostResponse> getPostResponses(Pageable pageable, Page<Post> postPage) {
        List<PostResponse> postResponseList = postPage.get().map(
                post -> PostResponse.factory(post)).collect(Collectors.toList());

        Page<PostResponse> postResponsePage =
                new PageImpl<>(postResponseList, pageable, postPage.getTotalElements());
        return postResponsePage;
    }

}
