package com.ayurveda.server.api.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailableTimeRequest {

    private int roomNumber;
    private LocalDate date;
    private String timeSlotId;
    private int maxPatientCount;
}
