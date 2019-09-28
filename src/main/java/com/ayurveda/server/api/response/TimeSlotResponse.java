package com.ayurveda.server.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSlotResponse {
    private String id;
    private String startTime;
    private String endTime;
}
