package com.ayurveda.server.domain;

public enum TreatmentStatus {
    RESERVED("RESERVED"),
    ONGOING("ONGOING"),
    FINISHED("FINISHED");

    String treatmentStatusString;

    TreatmentStatus(String treatmentStatusString) {
        this.treatmentStatusString = treatmentStatusString;
    }

    public String getAccountStatus() {
        return treatmentStatusString;
    }
}
