package com.example.ayur.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocSearchResultElement {
    private String doctorNic;
    private String doctorName;
    private String doctorSpecialization;

    private String appointmentDate;
    private String appointmentStartTime;
    private int currentPatientCount;
    private int totalPatientCount;
    private DoctorResponse doctorResponse;
    private AvailableTimeSlots availableTimeSlot;
}
