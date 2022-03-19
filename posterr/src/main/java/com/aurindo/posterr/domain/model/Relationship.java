package com.aurindo.posterr.domain.model;

import java.time.OffsetDateTime;

public class Relationship {

    private String id;
    private User follower;
    private User followed;
    private OffsetDateTime created;

}
