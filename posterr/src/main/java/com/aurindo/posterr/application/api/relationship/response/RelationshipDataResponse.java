package com.aurindo.posterr.application.api.relationship.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@Getter
@Builder
public class RelationshipDataResponse extends RepresentationModel<RelationshipDataResponse> {

    private String userId;
    private Long followers;
    private Long followeds;

}
