package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostListResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.PostRepository;
import com.aurindo.posterr.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        postRepository.findAll().forEach(post -> postRepository.delete(post));
        userRepository.findAll().forEach(user -> userRepository.delete(user));

        user = userRepository.save(User.builder().username("username").build());
    }

    @Test
    public void whenRequestPostDataFromUserShouldReturnCompletePostDataResponse() {

        postRepository.save(Post.builder().content("contentA").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentB").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentC").creator(user).type(Post.PostType.ORIGINAL).build()) ;

        String path = "/api/v1/post/%s/data";
        String url = String.format(path, user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<PostDataResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, PostDataResponse.class);
        PostDataResponse postDataResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postDataResponse.getUserId()).isEqualTo(user.getId());
        assertThat(postDataResponse.getTotal()).isEqualTo(3);
    }

    @Test
    public void whenRequestListPostsFromAllUserShouldReturnListPostsLimited() {

        User user2 = userRepository.save(User.builder().username("username2").build());

        postRepository.save(Post.builder().content("First post").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentB").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentC").creator(user).type(Post.PostType.ORIGINAL).build()) ;
        postRepository.save(Post.builder().content("Last post").creator(user2).type(Post.PostType.ORIGINAL).build()) ;

        String limit = "limit=10";
        String path = "/api/v1/post/from-all?%s";
        String url = String.format(path, limit);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<PostListResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, PostListResponse.class);
        PostListResponse postListResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postListResponse).isNotEqualTo(null);
        assertThat(postListResponse.getPostResponseList()).isNotEqualTo(null);
        assertThat(postListResponse.getPostResponseList().size()).isEqualTo(4);

        PostResponse postResponse = postListResponse.getPostResponseList().get(0);
        assertThat(postResponse.getContent()).isEqualTo("Last post");
        assertThat(postResponse.getCreator().getId()).isEqualTo(user2.getId());
        assertThat(postResponse.getCreator().getUserName()).isEqualTo("username2");
        assertThat(postResponse.getType()).isEqualTo(Post.PostType.ORIGINAL);
    }

}
