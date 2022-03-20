package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import com.aurindo.posterr.domain.model.Relationship;
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
public class RelationshipControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

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

        user = userRepository.save(User.builder().username("username").build());
    }

    @Test
    public void whenRequestRelationshipDataFromUserShouldReturnCompleteRelationshipDataResponse() {

        User userFollower = userRepository.save(User.builder().username("usernameA").build());
        User userFollowed = userRepository.save(User.builder().username("usernameB").build());

        relationshipRepository.save(Relationship.builder().follower(userFollower).followed(user).build());
        relationshipRepository.save(Relationship.builder().follower(user).followed(userFollowed).build());

        String path = "/api/v1/relationship/%s/data";
        String url = String.format(path, user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<RelationshipDataResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, RelationshipDataResponse.class);
        RelationshipDataResponse relationshipDataResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(relationshipDataResponse.getUserId()).isEqualTo(user.getId());
        assertThat(relationshipDataResponse.getFolloweds()).isEqualTo(1);
        assertThat(relationshipDataResponse.getFolloweds()).isEqualTo(1);
    }

}
