package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Document
public class Consultation {

    @Id
    private String id;

    private String patientId;

    @DBRef
    private Appointment appointment;

    private String examinationFindings;

    private String currentCondition;

    private LocalDate nextAppointmentDate;

    private LocalDate appointmentDate;

    private List<Prescription> internalPrescriptionList;

    private List<Prescription> externalPrescriptionList;

    private double totalCharge;

    private Treatments treatment;

    @Transient
    private List<Consultation> previousConsultationList;

    @Transient
    private AvailableTimeSlots currentSession;


}
