package com.aurindo.posterr.posterr.application.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
public class UserResponse {

    private String id;
    private String joinedDate;
    private Long numberFollowers;
    private Long numberFolloweds;
    private Long numberPosts;

}
