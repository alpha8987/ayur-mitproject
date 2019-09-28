package com.example.ayur.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponse implements Serializable {

    private String appointmentId;
    private Doctor doctor;
    private Patient patient;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate paymentMadeOn;

    //ipg details
    private String gatewayUrl;
    private String merchantId;
    private String returnUrl;
    private String cancelUrl;
    private String notifyUrl;

}
