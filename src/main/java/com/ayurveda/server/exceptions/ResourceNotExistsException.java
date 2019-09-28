package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ResourceNotExistsException extends ApiException {

    private static final ResourceNotExistsException RESOURCE_NOT_EXISTS_EXCEPTION = new ResourceNotExistsException();

    public ResourceNotExistsException() {
        super(new ErrorResponse("RESERR001",
                HttpStatus.NOT_FOUND.value(),
                "Resource not exists",
                "The referring resource does not exists"));
    }

    public static ResourceNotExistsException getInstance() {
        return RESOURCE_NOT_EXISTS_EXCEPTION;
    }

}
