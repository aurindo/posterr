package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;

    @BeforeEach
    public void init() {
        relationshipRepository.findAll().forEach(obj -> relationshipRepository.delete(obj));
        postRepository.findAll().forEach(obj -> postRepository.delete(obj));
        userRepository.findAll().forEach(obj -> userRepository.delete(obj));

        user = User.builder().username("John").dateJoined(new Date()).build();
        user = userRepository.save(user);
    }

    @Test
    public void savePost() {
        Post post = Post.builder().
                content("test").
                created(new Date()).
                creator(user).
                type(Post.PostType.ORIGINAL).build();

        postRepository.save(post);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = postRepository.findAllByOrderByCreatedDesc(pageable);

        assertThat(posts).isNotEqualTo(null);
        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

}
