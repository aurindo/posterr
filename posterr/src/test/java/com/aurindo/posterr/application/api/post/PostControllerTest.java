package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.post.response.PostListResponse;
import com.aurindo.posterr.application.api.post.response.PostResponse;
import com.aurindo.posterr.domain.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void whenRequestPostDataFromUserShouldReturnCompletePostDataResponse() {
        String userId = "1";
        String path = "/api/v1/post/%s/data";
        String url = String.format(path, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<PostDataResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, PostDataResponse.class);
        PostDataResponse postDataResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postDataResponse.getUserId()).isEqualTo(userId);
        assertThat(postDataResponse.getTotal()).isEqualTo(10L);
    }

    @Test
    public void whenRequestListPostsFromAllUserShouldReturnListPostsLimited() {
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
        assertThat(postListResponse.getPostResponseList().size()).isEqualTo(3);

        PostResponse postResponse = postListResponse.getPostResponseList().get(0);
        assertThat(postResponse.getContent()).isEqualTo("First post");
//        assertThat(postResponse.getCreated()).isEqualTo(3);
        assertThat(postResponse.getCreator().getId()).isEqualTo("1");
        assertThat(postResponse.getCreator().getUserName()).isEqualTo("John");
        assertThat(postResponse.getPostId()).isEqualTo("1");
        assertThat(postResponse.getType()).isEqualTo(Post.PostType.ORIGINAL);
    }

}
