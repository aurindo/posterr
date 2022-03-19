package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostListResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.domain.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class PostController implements PostResource {

    @Autowired
    private PostService postService;

    @Override
    public PostDataResponse fetchData(String userId) {
        postService.fetchPostDataFromUser(userId);
        return PostDataResponse.builder().userId(userId).total(10L).build();
    }

    @Override
    public PostListResponse fetchPostsFromAll(Integer limit) {

        List<PostResponse> postResponseList = postService.fetchPostsFromAll(limit).stream().map(
                post -> PostResponse.factory(post)).collect(Collectors.toList());

        return PostListResponse.builder().
                postResponseList(postResponseList).build();
    }
}
