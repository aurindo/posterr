package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/relationship")
public interface RelationshipResource {

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<RelationshipDataResponse> fetchData(@PathVariable(value = "userId", required = true) String userId);

}
