package com.aurindo.posterr.domain.relationship;

public interface RelationshipService {

    Long numberOfFollowersByUser(String userId);

    Long numberOffollowedsByUser(String userId);

    boolean isFollowing(String currentUserId, String otherUserId);

    Boolean followUser(String followerUserId, String followedUserId);

    void unfollowUser(String currentUserId, String otherUserId);
}
