package com.test.ecommerce.repository;

import com.test.ecommerce.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
}