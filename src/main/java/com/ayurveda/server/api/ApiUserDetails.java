package com.ayurveda.server.api;

import com.ayurveda.server.domain.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ApiUserDetails {

    @NotEmpty
    private String id;

    @NonNull
    @Length(min = 10, max = 10)
    private String nic;
    private String firstNames;
    private String lastName;
    private LocalDate dob;
    private String address;

    @Email
    private String email;

    private Date joinedDate = new Date();

    @NonNull
    @NotEmpty
    @Length(min = 10, max = 10)
    private String primaryContactNumber;
    private String emergencyContactPersonName;

    @NotNull
    @NotEmpty
    @Length(min = 10, max = 10)
    private String emergencyContactNumber;

    @NonNull
    @NotEmpty
    private String password;

    private AccountStatus accountStatus;
}
