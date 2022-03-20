package com.aurindo.posterr.application.api.relationship.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CreateRelationshipRequest {

    private String followerUserId;
    private String followedUserId;

}
