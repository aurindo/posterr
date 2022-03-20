package com.aurindo.posterr.domain.relationship;

public interface RelationshipService {

    Long numberOfFollowersByUser(String userId);

    Long numberOffollowedsByUser(String userId);

    boolean isFollowing(String currentUserId, String otherUserId);
}
