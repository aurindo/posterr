package com.aurindo.posterr.domain.model;

import java.time.OffsetDateTime;

public class Post {

    private String id;
    private String content;
    private OffsetDateTime created;
    private PostType type;
    private Post parent;

    enum PostType {
        ORIGINAL, REPORT, QUOTED
    }

}
