package com.ecommerce.project.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderItem;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.OrderRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.utils.ServiceUtils;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceUtils serviceUtils;

    public ResponseDto checkout(String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Cart cart = cartRepository.findByUser(user);
            if (cart == null || cart.getItems().isEmpty()) {
                throw new RuntimeException("Cart is empty");
            }

            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus("PENDING");
            order.setPaymentStatus("PENDING");

            List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                oi.setProduct(cartItem.getProduct());
                oi.setQuantity(cartItem.getQuantity());
                oi.setPrice(cartItem.getProduct().getPrice());
                return oi;
            }).collect(Collectors.toList());

            order.setItems(orderItems);

            BigDecimal totalAmount = orderItems.stream()
                    .map(oi -> oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalAmount(totalAmount);

            orderRepository.save(order);

            cart.getItems().clear();
            cartRepository.save(cart);

            responseDto.setData(serviceUtils.convertToDto(order));
            responseDto.setMessage("Checkout successful. Order placed.");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);
        } catch (Exception e) {
            responseDto.setMessage("Unable to checkout: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto getOrders(String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Order> orders = orderRepository.findByUser(user);

            responseDto.setData(
                    orders.stream()
                          .map(serviceUtils::convertToDto)
                          .collect(Collectors.toList())
                );
            responseDto.setMessage("Orders fetched successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);
        } catch (Exception e) {
            responseDto.setMessage("Unable to fetch orders: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto getOrderById(Long orderId) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            responseDto.setData(serviceUtils.convertToDto(order));
            responseDto.setMessage("Order fetched successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);
        } catch (Exception e) {
            responseDto.setMessage("Unable to fetch order: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto updateOrderStatus(Long orderId,String adminKey, String status) {
        ResponseDto responseDto = new ResponseDto();
        try {
        	if (!"X_ADMIN_USER".equalsIgnoreCase(adminKey)) {
				responseDto.setMessage("Access Denied");
				responseDto.setStatusCode(401);
				responseDto.setStatus("Failed");
				return responseDto;
			}
        	
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            order.setOrderStatus(status);
            orderRepository.save(order);

            responseDto.setData(serviceUtils.convertToDto(order));
            responseDto.setMessage("Order status updated successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);
        } catch (Exception e) {
            responseDto.setMessage("Unable to update order status: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }
}
