package com.aurindo.posterr.application.api.handlererror;

import com.aurindo.posterr.domain.exception.NotFoundException;
import com.aurindo.posterr.domain.exception.PosterrException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(
            Exception ex, WebRequest request) {
        NotFoundException nfEx =
                (NotFoundException) ex;

        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.NOT_FOUND.value()).
                errors(Arrays.asList(nfEx.getMessage())).build();

        return new ResponseEntity<>(errorResponse, new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " " + x.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder().
                status(status.value()).
                errors(errors).build();

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler({PosterrException.class })
    public ResponseEntity<Object> handlePosterrException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST.value()).
                errors(Arrays.asList(ex.getMessage())).build();

        return new ResponseEntity<>(errorResponse, new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleOtherException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder().
                status(500).
                errors(Arrays.asList(ex.getMessage())).build();

        return new ResponseEntity<>(errorResponse, new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

}
