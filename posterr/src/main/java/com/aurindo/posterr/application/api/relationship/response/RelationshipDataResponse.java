package com.aurindo.posterr.application.api.relationship.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
public class RelationshipDataResponse {

    private String userId;
    private Long followers;
    private Long followeds;

}
