package com.aurindo.posterr.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class Post {

    private String id;
    private String content;
    private OffsetDateTime created;
    private PostType type;
    private User creator;
    private Post parent;

    public enum PostType {
        ORIGINAL, REPORT, QUOTED
    }

}
