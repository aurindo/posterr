package com.aurindo.posterr.application.api.user;

import com.aurindo.posterr.application.api.user.response.UserDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController implements UserResource {

    @Override
    public ResponseEntity<UserDataResponse> fetchData(String userId) {

        UserDataResponse userDataResponse = UserDataResponse.builder().
                id(userId).
                joinedDate("date").
                userName("John").
                build();

        userDataResponse.add(linkTo(methodOn(UserController.class).fetchData(userId)).withSelfRel());

        return new ResponseEntity<>(userDataResponse, HttpStatus.OK);
    }
}
