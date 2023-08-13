package com.example.order.service;

import com.example.order.domain.OrderDAO;
import com.example.order.domain.OrderDTO;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderDTO> get(long id) {
        Optional<OrderDAO> dbValue = orderRepository.findById(id);
        return dbValue.map(OrderServiceImpl::map);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        OrderDAO save = orderRepository.save(map(orderDTO));
        return map(save);
    }

    @Override
    public List<OrderDTO> getAll() {
        final List<OrderDTO> orderDTOS = new LinkedList<>();
        for (OrderDAO orderDAO : orderRepository.findAll()) {
            orderDTOS.add(map(orderDAO));
        }
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getAll(List<Long> ids) {
        final List<OrderDTO> orderDTOS = new LinkedList<>();
        for (OrderDAO orderDAO : orderRepository.findAllById(ids)) {
            orderDTOS.add(map(orderDAO));
        }
        return orderDTOS;
    }

    @Override
    public Optional<OrderDTO> findOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId).map(OrderServiceImpl::map);
    }

    private static OrderDTO map(OrderDAO value) {
        return new OrderDTO(value.getId(), value.getUserId(), value.getProductIds());
    }

    private static OrderDAO map(OrderDTO value) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setUserId(value.getUserId());
        orderDAO.setProductIds(value.getProductIds());
        return orderDAO;
    }

}
