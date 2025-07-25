package com.saga.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEvent {

    private String orderId;
    private String productId;
    private String status;
    private String orderName;
    private UUID paymentId;

}
