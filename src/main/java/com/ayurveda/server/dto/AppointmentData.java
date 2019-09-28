package com.ayurveda.server.dto;

import com.ayurveda.server.domain.AvailableTimeSlots;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppointmentData {
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
