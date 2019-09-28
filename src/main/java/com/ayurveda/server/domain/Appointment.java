package com.ayurveda.server.domain;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "appointments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    private String id;
    @Indexed
    @DBRef(lazy = true)
    private Doctor doctor;
    @Nullable
    @DBRef(lazy = true)
    private Patient patient;
    @NonNull
    private LocalDate scheduledDate = LocalDate.now();
    @NonNull
    private LocalDate appointmentDate;
    @NonNull
    @DBRef(lazy = true)
    private TimeSlot appointmentTimeSlot;
    @NonNull
    private int appointmentNumber;
    @NonNull
    private int roomNumber;
    private double roomCharge;
    private double doctorCharge;
    private String gatewayPaymentId;
    private LocalDate paymentMadeOn;
    private boolean doctorNotification;
    private AppointmentStatus status = AppointmentStatus.INITIAL;
    private String appointmentRefNo;
}
