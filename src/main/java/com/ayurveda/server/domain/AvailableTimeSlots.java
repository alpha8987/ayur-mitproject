package com.ayurveda.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "available_time_slots")
@CompoundIndexes({
        @CompoundIndex(name = "available_time_slot_index",
                unique = true,
                def = "{'date' : 1, 'doctor' : 1,'TimeSlot' : 1}")
})
@Getter
@Setter
public class AvailableTimeSlots {
    @Id
    private String id;
    private int roomNumber;
    @NonNull
    @Field(value = "room_charge")
    private double roomCharge;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DBRef()
    private Doctor doctor;
    @DBRef()
    private TimeSlot timeSlot;
    private int currentPatientCount;
    private int maxPatientCount;
    private boolean isSessionStarted;
    private boolean isSessionEnded;
    private int currentNumber;
}
