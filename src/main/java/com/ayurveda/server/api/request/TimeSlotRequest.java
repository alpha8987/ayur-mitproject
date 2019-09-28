package com.ayurveda.server.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSlotRequest {
    private String startTime;
    private String endTime;
}
