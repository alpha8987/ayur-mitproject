package com.ayurveda.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MobileNotifyPayment {
    private String merchantId;
    private String orderId;
    private String paymentId;
    private String statusCode;
}
