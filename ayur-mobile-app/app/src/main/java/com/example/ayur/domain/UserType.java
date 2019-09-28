package com.example.ayur.domain;

import java.io.Serializable;

public enum UserType implements Serializable {
    PATIENT("PATIENT"),
    DOCTOR("DOCTOR"),
    PHARMACIST("PHARMACIST"),
    CASHIER("CASHIER"),
    ADMIN("ADMIN");

    String userRoleTypeString;

    UserType(String userRoleType) {
        this.userRoleTypeString = userRoleType;
    }

    public String getRole() {
        return userRoleTypeString;
    }
}
