package com.test.ecommerce.repository;

import com.test.ecommerce.entity.OrderHeader;
import com.test.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(OrderHeader order);
}