package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUser(User user);

}
