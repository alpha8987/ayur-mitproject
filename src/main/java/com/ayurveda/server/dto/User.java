package com.ayurveda.server.dto;


import com.ayurveda.server.domain.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class User {
    private String userName;
    private String status;
    private boolean isAdminUser;
    private UserType userType;
}
