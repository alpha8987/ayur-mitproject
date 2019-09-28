package com.example.ayur.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Appointment implements Serializable {
    private String id;
    private Doctor doctor;
    private Patient patient;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate scheduledDate = LocalDate.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate appointmentDate;
    private TimeSlot appointmentTimeSlot;
    private int appointmentNumber;
    private int roomNumber;
    private double roomCharge;
    private double doctorCharge;
    private String gatewayPaymentId;
    private LocalDate paymentMadeOn;
    private boolean doctorNotification;
    private AppointmentStatus status = AppointmentStatus.INITIAL;
    private String appointmentRefNo;
}
