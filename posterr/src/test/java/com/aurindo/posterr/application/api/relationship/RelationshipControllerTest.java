package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.request.CreateRelationshipRequest;
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

    @Test
    public void whenUserWouldLikeToKnowIfFollowAnotherUserShouldReturnTrueResponse() {

        User userFollower = userRepository.save(User.builder().username("usernameA").build());
        User userFollowed = userRepository.save(User.builder().username("usernameB").build());

        relationshipRepository.save(Relationship.builder().follower(userFollower).followed(userFollowed).build());

        String path = "/api/v1/relationship/%s/following/%s";
        String url = String.format(path, userFollower.getId(), userFollowed.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String isFollowingResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isFollowingResponse).isEqualTo("true");
    }

    @Test
    public void whenUserWouldLikeToKnowIfFollowAnotherUserShouldReturnFalseResponse() {

        User userFollower = userRepository.save(User.builder().username("usernameA").build());
        User userNotFollowed = userRepository.save(User.builder().username("NOTFOLLOWED").build());

        relationshipRepository.save(Relationship.builder().follower(userFollower).followed(user).build());

        String path = "/api/v1/relationship/%s/following/%s";
        String url = String.format(path, userFollower.getId(), userNotFollowed.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String isFollowingResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isFollowingResponse).isEqualTo("false");
    }

    @Test
    public void whenUserWouldLikeFollowAnotherUserShouldReturnFollowResponse() {

        User userFollower = userRepository.save(User.builder().username("usernameA").build());
        User userFollowed = userRepository.save(User.builder().username("usernameB").build());

        String path = "/api/v1/relationship";
        String url = String.format(path, userFollower.getId(), userFollowed.getId());

        CreateRelationshipRequest createRelationshipRequest =
                CreateRelationshipRequest.builder().
                        followedUserId(userFollowed.getId()).
                        followerUserId(userFollower.getId()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity(createRelationshipRequest, headers);

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String response = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Double check
        path = "/api/v1/relationship/%s/following/%s";
        url = String.format(path, userFollower.getId(), userFollowed.getId());

        headers.clearContentHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headers);

        //when
        responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String isFollowingResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isFollowingResponse).isEqualTo("true");
    }

    @Test
    public void whenUserWouldLikeToStopFollowAnotherUserShouldReturnTrueResponse() {

        User userFollower = userRepository.save(User.builder().username("usernameA").build());
        User userFollowed = userRepository.save(User.builder().username("usernameB").build());

        relationshipRepository.save(Relationship.builder().follower(userFollower).followed(userFollowed).build());

        String path = "/api/v1/relationship/%s/unfollow/%s";
        String url = String.format(path, userFollower.getId(), userFollowed.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        //when
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Double check
        path = "/api/v1/relationship/%s/following/%s";
        url = String.format(path, userFollower.getId(), userFollowed.getId());

        headers.clearContentHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headers);

        //when
        responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String isFollowingResponse = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(isFollowingResponse).isEqualTo("false");
    }

    @Test
    public void whenUserTryTofollowAgainAnUserShouldReturn400ErrorResponse() {

        User userFollowed = userRepository.save(User.builder().username("usernameB").build());

        relationshipRepository.save(Relationship.builder().follower(user).followed(userFollowed).build());

        String url = "/api/v1/relationship";

        CreateRelationshipRequest createRelationshipRequest =
                CreateRelationshipRequest.builder().
                        followedUserId(userFollowed.getId()).
                        followerUserId(user.getId()).build();


        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity(createRelationshipRequest, headers);

        //when
        ResponseEntity<RelationshipDataResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, RelationshipDataResponse.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
