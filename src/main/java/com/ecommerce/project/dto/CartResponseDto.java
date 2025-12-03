package com.ecommerce.project.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CartResponseDto {
    private Long cartId;
    private BigDecimal totalPrice;
    private List<CartItemDto> items;
}