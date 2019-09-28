package com.ayurveda.server.dto;

import com.ayurveda.server.domain.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class Member {

    private String userId;
    private UserType memberType;
    private String nic;
    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private LocalDate dob;
    private String address;
    private String country;
    private String mobileNo;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String notificationMethod;
    private String password;

    //fields only for doctor
    private String medicalLicense;
    private List<String> specializationList;
    private double consultationCharge;

}
