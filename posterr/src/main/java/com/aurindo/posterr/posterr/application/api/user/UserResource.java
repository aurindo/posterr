package com.aurindo.posterr.posterr.application.api.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/profile")
public interface UserResource {

    @GetMapping(
            value = "/{userID}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserResponse fetchUserData(@PathVariable(value = "userId", required = true) String userId);

}
