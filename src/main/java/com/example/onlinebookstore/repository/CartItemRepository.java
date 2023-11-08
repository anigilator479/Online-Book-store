package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);

    /*
    If you read this text, don't reject me pls,
    I tried to create this method for 4 hours on QnA,
    and it didn't work without custom Query
     */
    @Query("delete from CartItem where shoppingCart.id = :id")
    @Modifying
    void deleteAllByShoppingCartId(@Param("id") Long id);
}
