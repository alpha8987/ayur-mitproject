package com.ayurveda.server.domain;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "time_slot")
@Getter
@Setter
public class TimeSlot {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private Date startTime;
    @NonNull
    @Indexed(unique = true)
    private Date endTime;
}
