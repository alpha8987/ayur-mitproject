package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ServerErrorException extends ApiException {

    private static final ServerErrorException SERVER_ERROR_EXCEPTION = new ServerErrorException();

    public ServerErrorException() {
        super(new ErrorResponse("ERR001",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                "Error occurred while processing the request"));
    }

    public static ServerErrorException getInstance() {
        return SERVER_ERROR_EXCEPTION;
    }

}
