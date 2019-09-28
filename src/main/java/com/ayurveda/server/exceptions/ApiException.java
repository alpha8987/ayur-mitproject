package com.ayurveda.server.exceptions;


import com.ayurveda.server.api.response.ErrorResponse;

public class ApiException extends RuntimeException {

    private final ErrorResponse errorResponse;

    ApiException(ErrorResponse errorResponse){
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
