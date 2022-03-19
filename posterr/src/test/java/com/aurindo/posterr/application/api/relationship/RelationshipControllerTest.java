package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelationshipControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void whenRequestRelationshipDataFromUserShouldReturnCompleteRelationshipDataResponse() {
        String userId = "1";
        String path = "/api/v1/relationship/%s/data";
        String url = String.format(path, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<RelationshipDataResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, RelationshipDataResponse.class);
        RelationshipDataResponse relationshipDataResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(relationshipDataResponse.getUserId()).isEqualTo(userId);
        assertThat(relationshipDataResponse.getFolloweds()).isEqualTo(1);
        assertThat(relationshipDataResponse.getFolloweds()).isEqualTo(1);
    }

}
