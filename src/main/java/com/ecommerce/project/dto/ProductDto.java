package com.ecommerce.project.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDto {
	private String productName;

	private String productDescription;

	private BigDecimal price;

	private Integer stockQuantity;

	private String category;

	private String imageUrl;

	private Double rating;

}
