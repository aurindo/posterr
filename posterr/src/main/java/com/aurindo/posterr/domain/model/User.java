package com.aurindo.posterr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class User {

    private String id;
    private String username;
    private OffsetDateTime dateJoined;
    private Set<Relationship> relationshipSet;
    private List<Post> posts;

}
