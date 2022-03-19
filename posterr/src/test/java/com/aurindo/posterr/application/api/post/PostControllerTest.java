package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
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
        assertThat(postDataResponse.getTotal()).isEqualTo(1);
    }

}
