package com.saga.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private UUID paymentId;
    private String orderId;
    private String productId;
    private String status;
    private String orderName;


}
