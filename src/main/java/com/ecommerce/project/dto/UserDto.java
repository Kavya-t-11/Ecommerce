package com.ecommerce.project.dto;

import java.util.List;

import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.Order;

import lombok.Data;

@Data
public class UserDto {

	private String name;

	private String email;

	private String password;

	private String role;
	
	private List<Cart> carts;
	
	private List<Order> orders;
	
	 public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

	    public List<Cart> getCarts() {
	        return carts;
	    }

	    public void setCarts(List<Cart> carts) {
	        this.carts = carts;
	    }

	    public List<Order> getOrders() {
	        return orders;
	    }

	    public void setOrders(List<Order> orders) {
	        this.orders = orders;
	    }
	
	
	
}
