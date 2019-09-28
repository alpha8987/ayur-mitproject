package com.ayurveda.server.domain;

public enum AppointmentStatus {
    INITIAL, // payment is not completed
    PAYMENT_FAILED, // payment is not completed
    CONFIRMED, // payment is confirmed
    CANCELED_BY_DOCTOR,
    WAITING_CONSULTATION, // patient is present at the hospital
    POST_CONSULTATION, // patient is waiting for drugs to be dispatched
    POST_COLLECT, // patient is waiting for cashier to charge
    COMPLETED
}