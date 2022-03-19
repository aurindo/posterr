package com.aurindo.posterr.application.api.user;

import com.aurindo.posterr.application.api.user.response.UserDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

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
        assertThat(userDataResponse.getJoinedDate()).isEqualTo("date");
        assertThat(userDataResponse.getUserName()).isEqualTo("John");
    }

}
