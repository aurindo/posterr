package com.aurindo.posterr.domain.exception;

public class PageLimitException extends PosterrException {

    private static String message = "Number of items in a page must be equal less than %s";

    public PageLimitException(int rateLimit) {
        super(String.format(message, rateLimit));
    }

}
