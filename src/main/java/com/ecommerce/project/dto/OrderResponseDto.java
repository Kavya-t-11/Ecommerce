package com.ecommerce.project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDto {
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String paymentStatus;
    private String orderStatus;
    private List<OrderItemDto> items;
}