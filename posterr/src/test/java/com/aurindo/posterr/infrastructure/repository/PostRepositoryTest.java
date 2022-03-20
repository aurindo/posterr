package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void savePost() {

        User user = User.builder().username("John").dateJoined(OffsetDateTime.now()).build();
        user = userRepository.save(user);

        Post post = Post.builder().
                content("test").
                created(OffsetDateTime.now()).
                creator(user).
                type(Post.PostType.ORIGINAL).build();

        postRepository.save(post);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = postRepository.findAllByOrderByCreatedDesc(pageable);

        assertThat(posts).isNotEqualTo(null);
        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

}
