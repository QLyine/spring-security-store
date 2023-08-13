package com.example.order.repository;

import com.example.order.domain.OrderDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderDAO, Long> {
    Optional<OrderDAO> findByUserId(Long aLong);
}
