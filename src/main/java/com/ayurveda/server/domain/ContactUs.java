package com.ayurveda.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contactUs")
@Getter
@Setter
public class ContactUs {
    @Id
    private String contactUsId;
    private String name;
    private  String email;
    private  String message;
}
