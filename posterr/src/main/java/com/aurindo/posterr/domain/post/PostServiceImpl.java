package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public Post fetchPostDataFromUser(String userId) {
        return Post.builder().
                id("1").
                content("First post").
                created(OffsetDateTime.now()).
                type(Post.PostType.ORIGINAL).build();
    }

    @Override
    public List<Post> fetchPostsFromAll(Integer limit) {

        User user = User.builder().id("1").username("John").build();

        List<Post> posts = new ArrayList<>();
        posts.add(Post.builder().
                id("1").
                content("First post").
                created(OffsetDateTime.now()).
                type(Post.PostType.ORIGINAL).
                creator(user).
                build());
        posts.add(Post.builder().
                id("2").
                content("Second post").
                created(OffsetDateTime.now()).
                type(Post.PostType.ORIGINAL).
                creator(user).
                build());
        posts.add(Post.builder().
                id("3").
                content("Third post").
                created(OffsetDateTime.now()).
                type(Post.PostType.ORIGINAL).
                creator(user).
                build());

        return posts;
    }
}
