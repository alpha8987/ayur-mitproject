package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@ToString
public class Prescription {
    @DBRef
    private Drug drug;
    private String direction;
    private int qty;
}
