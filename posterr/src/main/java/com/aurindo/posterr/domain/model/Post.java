package com.aurindo.posterr.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;

    private String content;
    private LocalDateTime created;
    private PostType type;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne
    private Post parent;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    public enum PostType {
        ORIGINAL, REPORT, QUOTED
    }
}
