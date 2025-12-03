package com.ecommerce.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.utils.ServiceUtils;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ServiceUtils serviceUtils;

	public ResponseDto getAllProducts(int page, int size) {
		ResponseDto response = new ResponseDto();
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());
			Page<Product> productPage = productRepository.findAll(pageable);

			response.setStatus("SUCCESS");
			response.setMessage("Products fetched successfully");
			response.setStatusCode(200);
			response.setData(productPage.getContent());
			response.setTotalPages(productPage.getTotalPages());
			response.setTotalElements(productPage.getTotalElements());
		} catch (Exception e) {
			response.setStatus("FAILURE");
			response.setMessage(e.getMessage());
		}
		return response;
	}

	public ResponseDto getProductById(Long id) {
		ResponseDto response = new ResponseDto();
		try {
			Optional<Product> productResult = productRepository.findById(id);

			if (productResult.isPresent()) {
				Product product = productResult.get();

				response.setStatus("SUCCESS");
				response.setMessage("Products fetched successfully");
				response.setStatusCode(200);
				response.setData(product);

			} else {
				response.setStatus("Not Found");
				response.setMessage("Product Not found");
				response.setStatusCode(400);
			}

		} catch (Exception e) {
			response.setStatus("FAILURE");
			response.setMessage(e.getMessage());
		}
		return response;
	}

	public ResponseDto addProduct(String adminKey,ProductDto productDto) {
		ResponseDto response = new ResponseDto();
		try {
			
			if (!"X_ADMIN_USER".equalsIgnoreCase(adminKey)) {
				response.setMessage("Access Denied");
				response.setStatusCode(401);
				response.setStatus("Failed");
				return response;
			}
			Product product = serviceUtils.convertToEntity(productDto);
			productRepository.save(product);
			
			response.setStatus("SUCCESS");
			response.setMessage("Product Details saved successfully");
			response.setStatusCode(200);
			response.setData(product);

		} catch (Exception e) {
			response.setStatus("FAILURE");
			response.setMessage(e.getMessage());
		}
		return response;
	}

	public ResponseDto updateProduct(Long id,String adminKey,ProductDto productDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (!"X_ADMIN_USER".equalsIgnoreCase(adminKey)) {
				responseDto.setMessage("Access Denied");
				responseDto.setStatusCode(401);
				responseDto.setStatus("Failed");
				return responseDto;
			}
			
			Optional<Product> existingProduct = productRepository.findById(id);

			if (existingProduct.isPresent()) {
				Product productObject = existingProduct.get();
				productObject.setProductName(productDto.getProductName());
				productObject.setProductDescription(productDto.getProductDescription());
				productObject.setPrice(productDto.getPrice());
				productObject.setRating(productDto.getRating());
				productObject.setStockQuantity(productDto.getStockQuantity());
				productObject.setCategory(productDto.getCategory());
				productObject.setImageUrl(productDto.getImageUrl());
				productRepository.save(productObject);

				responseDto.setData(productObject);
				responseDto.setMessage("Product details updated Succesfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("Product not found");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to update product details");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

	public ResponseDto deleteProduct(Long id, String adminKey) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (!"X_ADMIN_USER".equalsIgnoreCase(adminKey)) {
				responseDto.setMessage("Access Denied");
				responseDto.setStatusCode(401);
				responseDto.setStatus("Failed");
				return responseDto;
			}

			Optional<Product> existingProduct = productRepository.findById(id);

			if (existingProduct.isPresent()) {
				Product productObject = existingProduct.get();
				productRepository.delete(productObject);

				responseDto.setData(productObject);
				responseDto.setMessage("Product deleted Successfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("Product does not exists");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to delete product details");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

}
