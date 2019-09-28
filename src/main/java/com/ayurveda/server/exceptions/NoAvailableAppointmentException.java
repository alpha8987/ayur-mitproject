package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class NoAvailableAppointmentException extends ApiException {

    private static final NoAvailableAppointmentException NO_AVAILABLE_APPOINTMENT_EXCEPTION = new NoAvailableAppointmentException();

    public NoAvailableAppointmentException() {
        super(new ErrorResponse("APPERR002",
                HttpStatus.NOT_FOUND.value(),
                "No available appointment",
                "No appointment found for given Id"));
    }

    public static NoAvailableAppointmentException getInstance() {
        return NO_AVAILABLE_APPOINTMENT_EXCEPTION;
    }

}
