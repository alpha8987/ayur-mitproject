package com.ayurveda.server.domain;

public enum UserType {
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
