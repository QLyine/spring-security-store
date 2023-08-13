package com.example.product.controllers;


import com.example.product.domain.ProductDTO;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/product")
	public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(required = false) List<Long> ids){
		if (ids != null && ids.size() > 0 ) {
			return ResponseEntity.ok(productService.getAll(ids));
		}

		return ResponseEntity.ok(productService.getAll());
	}

	@GetMapping(value = "/product/{id}")
	public ResponseEntity<Optional<ProductDTO>> getProductById(@PathVariable long id){
		return ResponseEntity.ofNullable(productService.get(id));
	}

	@PostMapping(value = "/product")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody  ProductDTO productDTO){
		return ResponseEntity.ofNullable(productService.save(productDTO));
	}
}
