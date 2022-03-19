package com.aurindo.posterr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String username;
    private OffsetDateTime dateJoined;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        dateJoined = OffsetDateTime.now();
    }

}
