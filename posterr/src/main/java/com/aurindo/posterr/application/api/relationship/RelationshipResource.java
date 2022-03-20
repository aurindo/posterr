package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.request.CreateRelationshipRequest;
import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/relationship")
public interface RelationshipResource {

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<RelationshipDataResponse> fetchData(
            @PathVariable(value = "userId", required = true) String userId);

    @GetMapping(
            value = "/{userId}/following/{otherUserId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> currentUserFollowing(
            @PathVariable(value = "userId", required = true) String userId,
            @PathVariable(value = "otherUserId", required = true) String otherUserId
    );

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> followUser(
            @RequestBody CreateRelationshipRequest createRelationshipRequest
    );

    @DeleteMapping(
            value = "/{followerUserId}/unfollow/{followedUserId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> unfollowUser(
            @PathVariable(value = "followerUserId", required = true) String followerUserId,
            @PathVariable(value = "followedUserId", required = true) String followedUserId
    );

}
