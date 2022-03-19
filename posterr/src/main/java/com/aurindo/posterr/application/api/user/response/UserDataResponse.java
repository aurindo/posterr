package com.aurindo.posterr.application.api.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@Getter
@Builder
public class UserDataResponse  extends RepresentationModel<UserDataResponse> {

    private String id;
    private String userName;
    private String joinedDate;

}
