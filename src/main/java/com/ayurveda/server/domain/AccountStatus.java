package com.ayurveda.server.domain;

public enum AccountStatus {
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
