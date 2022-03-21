package com.aurindo.posterr.domain.service.relationship;

import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationshipRulesImpl implements RelationshipRules {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public boolean doNotFollowYourSelf(String followerId, String followedId) {
        followerId = followerId == null ? "" : followerId;
        return followerId.equals(followedId);
    }

    @Override
    public boolean doNotFollowTwice(User follower, User followed) {
        relationshipRepository.isFollowing(follower, followed);
        return false;
    }
}
