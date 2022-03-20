package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.List;

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

        List<Post> posts = postRepository.findAllByOrderByCreatedDesc();

        assertThat(posts).isNotEqualTo(null);
        assertThat(posts.size()).isEqualTo(1);
    }

}
