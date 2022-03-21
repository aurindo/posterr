package com.aurindo.posterr.application.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Builder
public class ErrorResponse {

    @Builder.Default
    private Date timestamp = new Date();
    private int status;
    private List<String> errors;

}
