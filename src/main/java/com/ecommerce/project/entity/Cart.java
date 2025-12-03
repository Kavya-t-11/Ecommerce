package com.ecommerce.project.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "carts")
@Data
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();
	
    public void addProduct(Product product, int quantity) {
        boolean itemExists = false;

        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            newItem.setCart(this);
            items.add(newItem);
        }

        recalculateTotalPrice();
    }

    public void updateProductQuantity(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
                recalculateTotalPrice();
                return;
            }
        }
        throw new RuntimeException("Product not found in cart");
    }

    public void removeProduct(Long productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getProductId().equals(productId)) {
                iterator.remove();
                item.setCart(null); 
                recalculateTotalPrice();
                return;
            }
        }
        throw new RuntimeException("Product not found in cart");
    }

    public void recalculateTotalPrice() {
        totalPrice = items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }
    
    
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;

        if (items != null) {
            items.forEach(item -> item.setCart(this));
        }

        recalculateTotalPrice();
    }
    

}
