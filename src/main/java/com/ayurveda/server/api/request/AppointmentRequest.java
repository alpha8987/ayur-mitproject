package com.ayurveda.server.api.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class AppointmentRequest {

    private String patientNIC;
    private String patientFirstName;
    private String patientLastName;
    private String email;
    private String patientContactNumber;
    private String address;

    private String paymentType;
    private LocalDate appointmentDate;
    private String appointmentTimeSlotId;
    private boolean doctorNotificationsSubscribed;
}
