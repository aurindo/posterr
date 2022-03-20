package com.aurindo.posterr.domain.relationship;

import com.aurindo.posterr.domain.exception.NotFoundException;
import com.aurindo.posterr.domain.model.Relationship;
import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.RelationshipRepository;
import com.aurindo.posterr.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long numberOfFollowersByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId, User.class));

        return relationshipRepository.numberOfFollowersByUser(user);
    }

    @Override
    public Long numberOffollowedsByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId, User.class));

        return relationshipRepository.numberOffollowedsByUser(user);
    }

    @Override
    public boolean isFollowing(String currentUserId, String otherUserId) {
        User currentUser = userRepository.findById(currentUserId).orElseThrow(
                () -> new NotFoundException(otherUserId, User.class));
        User otherUser = userRepository.findById(otherUserId).orElseThrow(
                () -> new NotFoundException(otherUserId, User.class));

        Relationship relationship = relationshipRepository.isFollowing(currentUser, otherUser).orElse(null);

        return relationship != null ? true : false;
    }

    @Override
    public Boolean followUser(String followerUserId, String followedUserId) {
        User userFollower = userRepository.findById(followerUserId).orElseThrow(
                () -> new NotFoundException(followedUserId, User.class));
        User userFollowed = userRepository.findById(followedUserId).orElseThrow(
                () -> new NotFoundException(followedUserId, User.class));

        Relationship relationship = Relationship.builder().follower(userFollower).followed(userFollowed).build();
        relationshipRepository.save(relationship);

        return true;
    }

    @Override
    public void unfollowUser(String currentUserId, String otherUserId) {
        User currentUser = userRepository.findById(currentUserId).orElseThrow(
                () -> new NotFoundException(otherUserId, User.class));
        User otherUser = userRepository.findById(otherUserId).orElseThrow(
                () -> new NotFoundException(otherUserId, User.class));

        Optional<Relationship> relationshipOptional = relationshipRepository.isFollowing(currentUser, otherUser);
        relationshipOptional.ifPresent(relationship -> relationshipRepository.delete(relationship));
    }
}
