package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/getAllProducts")
	public ResponseDto getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return productService.getAllProducts(page, size);
	}

	@GetMapping("/getProductById")
	public ResponseDto getProductById(@RequestParam Long id) {
		return productService.getProductById(id);
	}

	@PostMapping("/addProduct")
	public ResponseDto addProduct(@RequestHeader(value = "ADMIN_KEY", required = false) String adminKey,
			@RequestBody ProductDto productDto) {
		return productService.addProduct(adminKey, productDto);
	}

	@PutMapping("/updateProduct")
	public ResponseDto updateProduct(@RequestParam Long id,
			@RequestHeader(value = "ADMIN_KEY", required = false) String adminKey, @RequestBody ProductDto productDto) {
		return productService.updateProduct(id, adminKey, productDto);
	}

	@DeleteMapping("/deleteProduct")
	public ResponseDto deleteProduct(@RequestParam Long id,
			@RequestHeader(value = "ADMIN_KEY", required = false) String adminKey) {
		return productService.deleteProduct(id, adminKey);
	}

}
