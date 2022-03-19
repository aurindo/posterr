package com.aurindo.posterr.application.api.user;

import com.aurindo.posterr.application.api.user.response.UserDataResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserResource {

    @Override
    public UserDataResponse fetchData(String userId) {
        return UserDataResponse.builder().
                id(userId).
                joinedDate("date").
                userName("John").
                build();
    }
}
