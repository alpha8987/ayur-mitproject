package com.ayurveda.server.dto;

import com.ayurveda.server.domain.TimeSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DoctorSessionInfo {
    private LocalDate sessionDate;
    private TimeSlot timeSlot;
    private int totalPatients;
    private long paidPatients;
    private long waitingPatients;
    private String timeSlotId;
    private boolean sessionCanBeStarted;
    private boolean isSessionStarted;
}
