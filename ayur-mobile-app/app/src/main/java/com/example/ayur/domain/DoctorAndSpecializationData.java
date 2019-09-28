package com.example.ayur.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorAndSpecializationData {
    List<DoctorResponse> allDoctors;
    Set<String> specializationSet;
}
