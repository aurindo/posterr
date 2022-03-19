package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.Post;

import java.util.List;

public interface PostService {

    Long fetchPostDataFromUser(String userId);

    List<Post> fetchPostsFromAll(Integer limit);

    Post getById(String postId);
}
