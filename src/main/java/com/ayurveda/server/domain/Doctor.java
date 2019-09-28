package com.ayurveda.server.domain;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "doctor")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    private String doctorId;
    @Indexed(unique = true)
    @NonNull
    @Field(value = "doctor_nic")
    private String doctorNic;
    @NonNull
    @Indexed(unique = true)
    @Field(value = "medical_license")
    private String medicalLicense;
    @Field(value = "first_names")
    private String firstNames;
    @NonNull
    @Indexed
    @Field(value = "last_name")
    private String lastName;
    @Indexed
    @NonNull
    @Field(value = "date_0f_birth")
    private LocalDate dob;
    @Field(value = "address")
    private String address;
    @Indexed
    @NonNull
    @Email
    private String email;
    @NonNull
    private Date joinedDate;
    @NonNull
    private List<String> specialization;
    @NonNull
    @Length(max = 10, min = 10)
    @Field(value = "primary_contact")
    private String primaryContactNumber;
    @NonNull
    @Field(value = "emergency_contact_name")
    private String emergencyContactPersonName;
    @NonNull
    @Length(min = 10, max = 10)
    @Field(value = "emergency_contact_number")
    private String emergencyContactPersonNumber;
    @NonNull
    @Field(value = "user_authenticate_data")
    @DBRef(lazy = true)
    private UserAuthenticateData authenticateData;
    @NonNull
    @Field(value = "consultation_charge")
    private double consultationCharge;
}
