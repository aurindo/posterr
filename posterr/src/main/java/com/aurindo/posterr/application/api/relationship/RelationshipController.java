package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import com.aurindo.posterr.domain.relationship.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<RelationshipDataResponse> fetchData(String userId) {

        RelationshipDataResponse relationshipDataResponse =
            RelationshipDataResponse.builder().
                userId(userId).
                followeds(relationshipService.numberOffollowedsByUser(userId)).
                followers(relationshipService.numberOfFollowersByUser(userId)).
                build();

        relationshipDataResponse.add(linkTo(methodOn(RelationshipController.class).fetchData(userId)).withSelfRel());

        return new ResponseEntity<>(relationshipDataResponse, HttpStatus.OK);
    }
}
