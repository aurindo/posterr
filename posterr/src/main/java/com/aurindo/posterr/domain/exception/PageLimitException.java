package com.aurindo.posterr.domain.exception;

public class PageLimitException extends PosterrException {

    private static String message = "Does not exceed page limit. limit=%s";

    public PageLimitException(int rateLimit) {
        super(String.format(message, rateLimit));
    }

}
