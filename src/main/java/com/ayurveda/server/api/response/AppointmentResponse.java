package com.ayurveda.server.api.response;

import com.ayurveda.server.domain.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private String appointmentId;
    private Doctor doctor;
    private Patient patient;
    private LocalDate appointmentDate;
    private TimeSlot appointmentTimeSlot;
    private int appointmentNumber;
    private int roomNumber;
    private AppointmentStatus status;
    private double roomCharge;
    private double doctorCharge;
    private String gatewayPaymentId;
    private double totalPayment;
    private boolean doctorNotificationSubscribed;
    private String appointmentRefNo;
    private LocalDate paymentMadeOn;

    //ipg details
    private String gatewayUrl;
    private String merchantId;
    private String returnUrl;
    private String cancelUrl;
    private String notifyUrl;

}
