package com.ayurveda.server.api.response;

import com.ayurveda.server.api.ApiUserDetails;
import com.ayurveda.server.domain.AccountStatus;
import com.ayurveda.server.domain.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponse extends ApiUserDetails {
    private UserType userType = UserType.PATIENT;
    private String patientId;
    private AccountStatus accountStatus;
}
