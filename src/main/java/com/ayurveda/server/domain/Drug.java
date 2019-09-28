package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document
public class Drug {
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private int availableQuantity;
    private int reorderLevel;
    private double unitPrice;
}
