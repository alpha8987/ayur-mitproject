package com.example.ayur.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorResponse extends ApiUserDetails implements Serializable {
    private String medicalLicense;
    private List<String> specializationList;
    private UserType userType = UserType.DOCTOR;
    private AccountStatus accountStatus;
    private List<AvailableTimeSlots> availableTimeSlotsList;
}
