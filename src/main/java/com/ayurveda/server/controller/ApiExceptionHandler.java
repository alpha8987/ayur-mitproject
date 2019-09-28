package com.ayurveda.server.controller;


import com.ayurveda.server.api.response.ErrorResponse;
import com.ayurveda.server.exceptions.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> apiExceptions(ApiException apiException) {
        return ResponseEntity.status(apiException.getErrorResponse().getHttpStatusCode()).
                body(apiException.getErrorResponse());
    }
}
