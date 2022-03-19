package com.aurindo.posterr.posterr.application.api.relationship;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationshipController implements RelationshipResource {

    public String test() {
        return "OK";
    }
}
