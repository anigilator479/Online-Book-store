package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.OrderItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderIdAndId(Long orderId, Long id);

    Set<OrderItem> findAllByOrderId(Long orderId);
}
