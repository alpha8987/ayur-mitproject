package com.ayurveda.server.api.response;

import com.ayurveda.server.api.ApiUserDetails;
import com.ayurveda.server.domain.AccountStatus;
import com.ayurveda.server.domain.AvailableTimeSlots;
import com.ayurveda.server.domain.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
public class DoctorResponse extends ApiUserDetails {

    @NonNull
    private String medicalLicense;
    private List<String> specializationList;
    private UserType userType = UserType.DOCTOR;
    private AccountStatus accountStatus;
    private List<AvailableTimeSlots> availableTimeSlotsList;
}
