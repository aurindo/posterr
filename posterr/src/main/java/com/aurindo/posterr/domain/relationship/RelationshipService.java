package com.aurindo.posterr.domain.relationship;

import com.aurindo.posterr.domain.model.User;

public interface RelationshipService {

    Long numberOfFollowersByUser(String userId);

    Long numberOffollowedsByUser(String userId);

}
