package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/post")
public interface PostResource {

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    PostDataResponse fetchData(@PathVariable(value = "userId", required = true) String userId);

}
