package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.request.CreatePostRequest;
import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.PostRepository;
import com.aurindo.posterr.infrastructure.repository.RelationshipRepository;
import com.aurindo.posterr.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    private User user;

    @BeforeEach
    public void init() {
        relationshipRepository.findAll().forEach(obj -> relationshipRepository.delete(obj));
        postRepository.findAll().forEach(obj -> postRepository.delete(obj));
        userRepository.findAll().forEach(obj -> userRepository.delete(obj));

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

        int page = 0;
        int size = 10;
        String path = "/api/v1/post/from-all?page=%s&size=%s";
        String url = String.format(path, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String searchResult = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(searchResult).isNotEqualTo(null);
    }

    @Test
    public void whenRequestListPostsFromUserShouldReturnListPostsFromUserLimited() {
        User user2 = userRepository.save(User.builder().username("username2").build());

        postRepository.save(Post.builder().content("First post").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentB").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("contentC").creator(user).type(Post.PostType.ORIGINAL).build()) ;
        postRepository.save(Post.builder().content("Last post").creator(user2).type(Post.PostType.ORIGINAL).build()) ;

        int page = 0;
        int size = 5;
        String path = "/api/v1/post/from-user?userId=%s&page=%s&size=%s";
        String url = String.format(path, user.getId(), page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String searchResult = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(searchResult).isNotEqualTo(null);
    }

    @Test
    public void whenRequestToCreatePostShouldReturnPostCreated() {
        String url = "/api/v1/post";

        CreatePostRequest createPostRequest =
                CreatePostRequest.builder().
                        content("First post").
                        userId(user.getId()).
                        type(Post.PostType.ORIGINAL.toString()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity(createPostRequest, headers);

        //when
        ResponseEntity<PostResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, PostResponse.class);
        PostResponse postResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getCreator().getId()).isEqualTo(user.getId());
        assertThat(postResponse.getContent()).isEqualTo("First post");
        assertThat(postResponse.getType()).isEqualTo(Post.PostType.ORIGINAL);
    }

    @Test
    public void whenUserTryExceedRateLimitShouldReturnError() {

        postRepository.save(Post.builder().content("A").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("B").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("C").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("D").creator(user).type(Post.PostType.ORIGINAL).build());
        postRepository.save(Post.builder().content("E").creator(user).type(Post.PostType.ORIGINAL).build());

        String url = "/api/v1/post";

        CreatePostRequest createPostRequest =
                CreatePostRequest.builder().
                        content("Sixth").
                        userId(user.getId()).
                        type(Post.PostType.ORIGINAL.toString()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity(createPostRequest, headers);

        //when
        ResponseEntity<PostResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, PostResponse.class);
        PostResponse postResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
