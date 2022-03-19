package com.aurindo.posterr.application.api.relationship;

import com.aurindo.posterr.application.api.relationship.response.RelationshipDataResponse;
import com.aurindo.posterr.application.api.user.response.UserDataResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationshipController implements RelationshipResource {

    @Override
    public RelationshipDataResponse fetchData(String userId) {

        return RelationshipDataResponse.builder().userId(userId).followeds(1L).followers(1L).build();


    }
}
