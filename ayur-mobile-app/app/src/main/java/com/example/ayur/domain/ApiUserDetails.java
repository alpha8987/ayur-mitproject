package com.example.ayur.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUserDetails {

    private String id;
    private String nic;
    private String firstNames;
    private String lastName;
    private Date dob;
    private String address;
    private String email;
    private Date joinedDate;
    private String primaryContactNumber;
    private String emergencyContactPersonName;
    private String emergencyContactNumber;
    private String password;
    private AccountStatus accountStatus;
}
