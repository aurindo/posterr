package com.aurindo.posterr.posterr.application.api.user;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserResource {
    @Override
    public UserResponse fetchUserData(String userId) {
        return UserResponse.builder().
                id("test").
                joinedDate("date").
                numberFolloweds(1L).
                numberFollowers(1L).
                numberPosts(1L).
                build();
    }
}
