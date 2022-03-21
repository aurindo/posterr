package com.aurindo.posterr.domain.model;

import com.aurindo.posterr.domain.validation.AlphanumericConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    @Max(value = 14, message = "Username not be greater than 14")
    @AlphanumericConstraint
    private String username;

    private Date dateJoined;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        dateJoined = new Date();
    }

}
