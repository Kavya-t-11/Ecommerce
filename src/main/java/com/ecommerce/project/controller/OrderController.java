package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/checkout")
	public ResponseDto checkout(@RequestParam String email) {
		return orderService.checkout(email);
	}

	
	@GetMapping("/getOrders")
	public ResponseDto getOrders(@RequestParam String email) {
		return orderService.getOrders(email);
	}

	@GetMapping("/getOrderById")
	public ResponseDto getOrderById(@RequestParam Long id) {
		return orderService.getOrderById(id);
	}

	@PutMapping("/updateOrderStatus")
	public ResponseDto updateOrderStatus(@RequestParam Long id,@RequestHeader(value = "ADMIN_KEY", required = false) String adminKey, @RequestParam String status) {
		return orderService.updateOrderStatus(id, adminKey,status);
	}
}
