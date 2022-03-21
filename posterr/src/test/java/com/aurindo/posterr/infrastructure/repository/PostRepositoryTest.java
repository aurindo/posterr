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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                created(LocalDateTime.now()).
                creator(user).
                type(Post.PostType.ORIGINAL).build();

        postRepository.save(post);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = postRepository.findAllByOrderByCreatedDesc(pageable);

        assertThat(posts).isNotEqualTo(null);
        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void numberPostsFromUserToday() {
        Post post = Post.builder().
                content("test").
                created(LocalDateTime.now()).
                creator(user).
                type(Post.PostType.ORIGINAL).build();

        Post post2 = Post.builder().
                content("test2").
                created(LocalDateTime.now()).
                creator(user).
                type(Post.PostType.ORIGINAL).build();

        postRepository.save(post);
        postRepository.save(post2);

        Long postNumber = postRepository.numberPostsFromUserToday(
                user,
                LocalDate.now().atTime(0,0,0,0));

        assertThat(postNumber).isNotEqualTo(null);
        assertThat(postNumber).isEqualTo(2);
    }

}
