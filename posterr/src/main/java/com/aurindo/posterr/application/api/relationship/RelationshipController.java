package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.request.CreateRelationshipRequest;
import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import com.aurindo.posterr.domain.relationship.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RelationshipController implements RelationshipResource {

    @Autowired
    private RelationshipService relationshipService;

    @Override
    public ResponseEntity<String> currentUserFollowing(String userId, String otherUserId) {
        Boolean isFollowing = relationshipService.isFollowing(userId, otherUserId);

        return ResponseEntity.
                ok().
                body(isFollowing.toString());
    }

    @Override
    public ResponseEntity<String> followUser(CreateRelationshipRequest createRelationshipRequest) {
        Boolean isFollowing = relationshipService.followUser(
                createRelationshipRequest.getFollowerUserId(), createRelationshipRequest.getFollowedUserId());

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(isFollowing.toString());
    }

    @Override
    public ResponseEntity<String> unfollowUser(String followerUserId, String followedUserId) {
        relationshipService.unfollowUser(followerUserId, followedUserId);

        return ResponseEntity.
                noContent().
                build();
    }

    @Override
    public ResponseEntity<RelationshipDataResponse> fetchData(String userId) {
        RelationshipDataResponse relationshipDataResponse =
            RelationshipDataResponse.builder().
                userId(userId).
                followeds(relationshipService.numberOffollowedsByUser(userId)).
                followers(relationshipService.numberOfFollowersByUser(userId)).
                build();

        relationshipDataResponse.add(linkTo(methodOn(RelationshipController.class).fetchData(userId)).withSelfRel());

        return ResponseEntity.
                ok().
                contentType(MediaTypes.HAL_JSON).
                body(relationshipDataResponse);
    }
}
