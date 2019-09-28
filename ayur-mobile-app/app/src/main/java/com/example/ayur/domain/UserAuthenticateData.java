package com.example.ayur.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticateData implements Serializable {

    private String id;
    private String userName;
    private String password;
    private UserType userType;
    private Set<UserType> userRoleList;
    private AccountStatus accountStatus;
}
