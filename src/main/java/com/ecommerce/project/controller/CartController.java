package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/addToCart")
	public ResponseDto addToCart(@RequestParam Long productId, @RequestParam String email, @RequestParam int quantity) {
		return cartService.addToCart(productId, email, quantity);
	}

	@PutMapping("/updateCartItem")
	public ResponseDto updateCartItem(@RequestParam Long productId, @RequestParam String email,
			@RequestParam int quantity) {
		return cartService.updateCartItem(productId, email, quantity);
	}

	@DeleteMapping("/removeFromCart")
	public ResponseDto removeFromCart(@RequestParam Long productId, @RequestParam String email) {
		return cartService.removeFromCart(productId, email);
	}

	@GetMapping("/getCart")
	public ResponseDto getCart(@RequestParam String email) {
		return cartService.getCart(email);
	}
}
