package com.aurindo.posterr.application.api.user;

import com.aurindo.posterr.application.api.user.response.UserDataResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/user")
public interface UserResource {

    @GetMapping(
            value = "/{userId}/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserDataResponse fetchData(@PathVariable(value = "userId", required = true) String userId);

}
