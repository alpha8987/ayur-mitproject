package com.ayurveda.server.domain;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Document(collection = "user_authenticate_data")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateData {

    @Id
    private String id;

    @NonNull
    @NotEmpty
    @Indexed(unique = true)
    @Field(value = "user_name")
    private String userName;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    private UserType userType;

    private Set<UserType> userRoleList;

    @NonNull
    private AccountStatus accountStatus;
}
