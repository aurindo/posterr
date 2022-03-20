package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Relationship;
import com.aurindo.posterr.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FollowersAndFollowingTest {

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
    public void AddFollowers() {
        User user = User.builder().username("John").dateJoined(OffsetDateTime.now()).build();
        User follower1 = User.builder().username("Follower1").dateJoined(OffsetDateTime.now()).build();
        User follower2 = User.builder().username("Follower2").dateJoined(OffsetDateTime.now()).build();

        user = userRepository.save(user);
        follower1 = userRepository.save(follower1);
        follower2 = userRepository.save(follower2);

        Relationship relationship1 = Relationship.builder().followed(user).follower(follower1).build();
        Relationship relationship2 = Relationship.builder().followed(user).follower(follower2).build();

        relationshipRepository.save(relationship1);
        relationshipRepository.save(relationship2);

        Long numberFollowers = relationshipRepository.numberOfFollowersByUser(user);
        Long numberFolloweds = relationshipRepository.numberOffollowedsByUser(user);

        assertThat(numberFollowers).isEqualTo(2);
        assertThat(numberFolloweds).isEqualTo(0);
    }

    @Test
    public void whenUserWouldLikeKnowIfFollowingOtherUserShouldReturnTrueOrFalse() {
        User user = User.builder().username("John").dateJoined(OffsetDateTime.now()).build();
        User currentUser = User.builder().username("CurrentUser").dateJoined(OffsetDateTime.now()).build();
        User otherUser = User.builder().username("OtherUser").dateJoined(OffsetDateTime.now()).build();

        user = userRepository.save(user);
        currentUser = userRepository.save(currentUser);
        otherUser = userRepository.save(otherUser);

        Relationship relationship1 = Relationship.builder().followed(otherUser).follower(currentUser).build();
        Relationship relationship2 = Relationship.builder().followed(user).follower(otherUser).build();

        relationshipRepository.save(relationship1);
        relationshipRepository.save(relationship2);

        Optional<Relationship> relationshipResponse = relationshipRepository.isFollowing(currentUser, otherUser);

        assertThat(relationshipResponse.get()).isNotEqualTo(null);
    }

}
