package com.ayurveda.server.domain;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "patient")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    private String patientId;

    @Indexed(unique = true)
    @NonNull
    @Field(value = "patient_nic")
    private String patientNic;

    @Field(value = "first_names")
    private String firstNames;

    @Indexed
    @Field(value = "last_name")
    private String lastName;
    private String title;

    @Indexed
    @Field(value = "date_0f_birth")
    private LocalDate dob;

    @Field(value = "address")
    private String address;

    private String country;

    @Indexed
    @Nullable
    private String email;

    @NonNull
    private Date joinedDate;

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

    private String notificationMethod;
}
