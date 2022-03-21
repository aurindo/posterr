package com.aurindo.posterr.domain.exception;

public class RateLimitException extends PosterrException {

    private static String message = "Does not exceed rate limit. Entity=%s, user=%s, limit=%s";


    public RateLimitException(Class clazz, String userId, int rateLimit) {
        super(String.format(message, clazz.getSimpleName(), userId, rateLimit));
    }

}
