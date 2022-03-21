package com.aurindo.posterr.domain.service.relationship;

import com.aurindo.posterr.domain.model.User;

public interface RelationshipRules {

    boolean doNotFollowYourSelf(String followerId, String followedId);

    boolean doNotFollowTwice(User follower, User followed);

}
