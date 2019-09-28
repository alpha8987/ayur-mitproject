package com.example.ayur.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentData implements Serializable {
    private boolean isMember;
    private boolean needDoctorNotification;
    private String title;
    private String firstName;
    private String lastName;
    private String nic;
    private String area;
    private String mobile;
    private String email;
    private boolean isLocal;
    private boolean acceptedTerms;
    private AvailableTimeSlots availableTimeSlot;
    private String paymentMode;
    private String appointmentDoctorId;
    private String preferredNotificationMethod;

}
