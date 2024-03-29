package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Relationship;
import com.aurindo.posterr.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RelationshipRepository extends CrudRepository<Relationship, String> {

    @Query("select count(*) from Relationship p where p.followed = :user")
    Long numberOfFollowersByUser(User user);

    @Query("select count(*) from Relationship p where p.follower= :user")
    Long numberOffollowedsByUser(User user);

    @Query("from Relationship p where p.follower = :currentUser and p.followed = :otherUser")
    Optional<Relationship> isFollowing(User currentUser, User otherUser);
}
