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

@Document(collection = "system_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    @Id
    private String  id;

    @Indexed(unique = true)
    @NonNull
    @Field(value = "cashier_nic")
    private String userNic;

    private UserType userType;

    @Field(value = "first_names")
    private String firstNames;

    @Indexed
    @Field(value = "last_name")
    private String lastName;

    private String title;

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

    private String mobileNo;

    @NonNull
    private Date joinedDate;

    @NonNull
    @Length(max = 10, min = 10)
    @Field(value = "primary_contact")
    private int primaryContactNumber;

    @NonNull
    @Field(value = "emergency_contact_name")
    private String emergencyContactPersonName;

    @NonNull
    @Length(min = 10, max = 10)
    @Field(value = "emergency_contact_number")
    private String emergencyContactPersonNumber;

    @NonNull
    @DBRef(db = "user_authenticate_data")
    private UserAuthenticateData authenticateData;
}
