package com.ayurveda.server.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private int httpStatusCode;
    private String description;
    private String additionalDetails;
}
