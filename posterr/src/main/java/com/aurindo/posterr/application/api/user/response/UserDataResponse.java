package com.aurindo.posterr.application.api.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
public class UserDataResponse {

    private String id;
    private String userName;
    private String joinedDate;

}
