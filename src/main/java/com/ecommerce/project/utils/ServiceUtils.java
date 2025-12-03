package com.ecommerce.project.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.project.dto.CartItemDto;
import com.ecommerce.project.dto.CartResponseDto;
import com.ecommerce.project.dto.OrderItemDto;
import com.ecommerce.project.dto.OrderResponseDto;
import com.ecommerce.project.dto.ProductDto;
import com.ecommerce.project.dto.UserDto;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderItem;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.entity.User;

@Component
public class ServiceUtils {

	@Autowired
	private ModelMapper modelMapper;	

	public User convertToEntity(UserDto dto) {
	    return modelMapper.map(dto, User.class);
	}

	public UserDto convertToDto(User entity) {
	    return modelMapper.map(entity, UserDto.class);
	}
	
	public Product convertToEntity(ProductDto dto) {
	    return modelMapper.map(dto, Product.class);
	}

	public ProductDto convertToDto(Product entity) {
	    return modelMapper.map(entity, ProductDto.class);
	}
	
	public CartResponseDto convertToDto(Cart cart) {
       return modelMapper.map(cart,CartResponseDto.class);
    }

    public CartItemDto convertToDto(CartItem item) {
       return modelMapper.map(item,CartItemDto.class);
    }
    
    public OrderResponseDto convertToDto(Order order) {
        return modelMapper.map(order,OrderResponseDto.class);
    }

    public OrderItemDto convertToDto(OrderItem item) {
    	return modelMapper.map(item,OrderItemDto.class);
    }


}
