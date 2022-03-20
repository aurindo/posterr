package com.aurindo.posterr.application.api.user;

import com.aurindo.posterr.application.api.format.DateFormatter;
import com.aurindo.posterr.application.api.user.response.UserDataResponse;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void init() {
        relationshipRepository.findAll().forEach(obj -> relationshipRepository.delete(obj));
        postRepository.findAll().forEach(obj -> postRepository.delete(obj));
        userRepository.findAll().forEach(obj -> userRepository.delete(obj));
    }

    @Test
    public void whenRequestUserDataFromUserShouldReturnCompleteUserDataResponse() {
        String userId = "1";
        String path = "/api/v1/user/%s/data";
        String url = String.format(path, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<UserDataResponse> responseEntity =
                restTemplate.exchange(url,HttpMethod.GET, requestEntity, UserDataResponse.class);
        UserDataResponse userDataResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userDataResponse.getId()).isEqualTo(userId);
        assertThat(userDataResponse.getJoinedDate()).isEqualTo(DateFormatter.formatter.format(new Date()));
        assertThat(userDataResponse.getUserName()).isEqualTo("John");
    }

}
