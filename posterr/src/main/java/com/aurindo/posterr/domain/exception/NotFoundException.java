package com.aurindo.posterr.domain.exception;

import javax.persistence.EntityNotFoundException;

public class NotFoundException extends EntityNotFoundException {

    private static String message = "Not found: Entity=%s, id=%s";

    public NotFoundException(String id, Class clazz) {
        super(String.format(message, clazz.getSimpleName(), id));
    }

}
