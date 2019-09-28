package com.ayurveda.server.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DoctorAndSpecializationData {
    List<DoctorResponse> allDoctors;
    Set<String> specializationSet;
}
