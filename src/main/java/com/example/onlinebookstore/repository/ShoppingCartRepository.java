package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findShoppingCartByUser(User user);
}
