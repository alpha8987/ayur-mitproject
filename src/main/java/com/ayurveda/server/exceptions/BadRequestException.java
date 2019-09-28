package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    private static final BadRequestException BAD_REQUEST_EXCEPTION = new BadRequestException();

    public BadRequestException() {
        super(new ErrorResponse("REQERR001",
                HttpStatus.BAD_REQUEST.value(),
                "Request parameter error",
                "The request parameters are not correct"));
    }

    public static BadRequestException getInstance() {
        return BAD_REQUEST_EXCEPTION;
    }

}
