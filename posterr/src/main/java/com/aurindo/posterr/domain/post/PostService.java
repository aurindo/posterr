package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long numberPostsFromUser(String userId);

    Page<Post> fetchPostsFromAll(Pageable pageable);

    Post getById(String postId);

    Page<Post> fetchMyPosts(String userId, Pageable pageable);

    Post createPost(String userId, String content, String parentId, String type);
}
