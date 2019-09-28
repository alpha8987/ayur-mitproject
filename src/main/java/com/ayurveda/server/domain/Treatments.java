package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "treatments")
@Getter
@Setter
@ToString
public class Treatments {
    @Id
    private String treatmentId;
    private String type;
    @TextIndexed
    private String description;
    private String image;
}
