package com.ayurveda.server.exceptions;

import com.ayurveda.server.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class NoAvailableTimeSlotException extends ApiException {

    private static final NoAvailableTimeSlotException NO_AVAILABLE_TIME_SLOT_EXCEPTION = new NoAvailableTimeSlotException();

    public NoAvailableTimeSlotException() {
        super(new ErrorResponse("APPRR001",
                HttpStatus.NOT_FOUND.value(),
                "No available time slot",
                "All appointments are full for the selected time slot"));
    }

    public static NoAvailableTimeSlotException getInstance() {
        return NO_AVAILABLE_TIME_SLOT_EXCEPTION;
    }

}
