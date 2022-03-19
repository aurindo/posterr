package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.Relationship;
import com.aurindo.posterr.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FollowersAndFollowingTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationshipRepository relationshipRepository;

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

}
