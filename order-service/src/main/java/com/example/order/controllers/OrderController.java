package com.example.order.controllers;


import com.example.order.domain.OrderDTO;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/order")
	public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam(required = false) List<Long> ids, @RequestParam(required = false) Long userId){
		if (ids != null && ids.size() > 0 ) {
			return ResponseEntity.ok(orderService.getAll(ids));
		}

		if(userId != null) {
			return ResponseEntity.ok(orderService.findOrderByUserId(userId).map(Collections::singletonList).orElse(Collections.emptyList()));
		}
		return ResponseEntity.ok(orderService.getAll());
	}

	@GetMapping(value = "/order/{id}")
	public ResponseEntity<Optional<OrderDTO>> getProductById(@PathVariable long id){
		return ResponseEntity.ofNullable(orderService.get(id));
	}

	@PostMapping(value = "/order")
	public ResponseEntity<OrderDTO> createProduct(@RequestBody OrderDTO orderDTO){
		return ResponseEntity.ofNullable(orderService.save(orderDTO));
	}
}
