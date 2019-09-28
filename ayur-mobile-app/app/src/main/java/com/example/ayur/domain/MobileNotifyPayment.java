package com.example.ayur.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MobileNotifyPayment implements Serializable {
    private String merchantId;
    private String orderId;
    private String paymentId;
    private String statusCode;
}
