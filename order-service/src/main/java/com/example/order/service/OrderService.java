package com.example.order.service;


import com.example.order.domain.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderDTO> get(long id);
    OrderDTO save(OrderDTO orderDTO);
    List<OrderDTO> getAll();


    List<OrderDTO> getAll(List<Long> ids);


    Optional<OrderDTO> findOrderByUserId(Long userId);
}
