package com.ecommerce.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.utils.ServiceUtils;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private ServiceUtils serviceUtils;

    public ResponseDto addToCart(Long productId,String email, int quantity) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Cart cart = cartRepository.findByUser(userRepository.findByEmail(email).get());
            if (cart == null) {
                cart = new Cart();
                cart.setUser(userRepository.findByEmail(email).get());
            }

            cart.addProduct(product, quantity);
            cartRepository.save(cart);

            responseDto.setData(serviceUtils.convertToDto(cart));
            responseDto.setMessage("Product added to cart successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);

        } catch (Exception e) {
            responseDto.setMessage("Unable to add product to cart: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto updateCartItem(Long productId,String email, int quantity) {
        ResponseDto responseDto = new ResponseDto();
        try {
			Cart cart = cartRepository.findByUser(userRepository.findByEmail(email).get());
            cart.updateProductQuantity(productId, quantity);
            cartRepository.save(cart);

            responseDto.setData(serviceUtils.convertToDto(cart));
            responseDto.setMessage("Cart updated successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);

        } catch (Exception e) {
            responseDto.setMessage("Unable to update cart: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto removeFromCart(Long productId,String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Cart cart = cartRepository.findByUser(userRepository.findByEmail(email).get());
            cart.removeProduct(productId);
            cartRepository.save(cart);

            responseDto.setData(serviceUtils.convertToDto(cart));
            responseDto.setMessage("Product removed from cart successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);

        } catch (Exception e) {
            responseDto.setMessage("Unable to remove product: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    public ResponseDto getCart(String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Cart cart = cartRepository.findByUser(userRepository.findByEmail(email).get());

            responseDto.setData(serviceUtils.convertToDto(cart));
            responseDto.setMessage("Cart fetched successfully");
            responseDto.setStatus("Success");
            responseDto.setStatusCode(200);

        } catch (Exception e) {
            responseDto.setMessage("Unable to fetch cart: " + e.getMessage());
            responseDto.setStatus("Failed");
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }
}

