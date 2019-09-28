package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
public class PasswordResetToken {
    private String userName;
    private String oldPassword;
    private  String newPassword;
}
