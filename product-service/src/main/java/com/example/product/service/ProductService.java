package com.example.product.service;

import com.example.product.domain.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> get(long id);
    ProductDTO save(ProductDTO productDTO);
    List<ProductDTO> getAll();


    List<ProductDTO> getAll(List<Long> ids);
}
