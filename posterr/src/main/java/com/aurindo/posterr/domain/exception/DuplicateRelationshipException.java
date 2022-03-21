package com.aurindo.posterr.domain.exception;

public class DuplicateRelationshipException extends PosterrException {

    private static String message = "Does not have a duplicate relationship. follower=%s, following=%s";

    public DuplicateRelationshipException(String follower, String following) {
        super(String.format(message, follower, follower));
    }

}
