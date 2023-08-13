package com.example.product.repository;

import com.example.product.domain.ProductDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductDAO, Long> {
}
