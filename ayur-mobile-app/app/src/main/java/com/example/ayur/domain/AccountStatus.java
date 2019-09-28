package com.example.ayur.domain;

import java.io.Serializable;

public enum AccountStatus implements Serializable {
    REGISTERED("REGISTERED"),
    ACTIVATED("ACTIVATED"),
    DELETED("DELETED"),
    LOCKED("LOCKED");

    String accountStatusString;

    AccountStatus(String accountStatusString) {
        this.accountStatusString = accountStatusString;
    }

    public String getAccountStatus() {
        return accountStatusString;
    }
}
