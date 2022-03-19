package com.aurindo.posterr.posterr.domain.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor()
public class User {

    private String id;
    private String username;
    private OffsetDateTime dateJoined;
    private Set<Relationship> relationshipSet;
    private List<Post> posts;

}
