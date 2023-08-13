package com.example.product.service;

import com.example.product.domain.ProductDAO;
import com.example.product.domain.ProductDTO;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> get(long id) {
        Optional<ProductDAO> dbValue = productRepository.findById(id);
        return dbValue.map(ProductServiceImpl::map);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        ProductDAO save = productRepository.save(map(productDTO));
        return map(save);
    }

    @Override
    public List<ProductDTO> getAll() {
        final List<ProductDTO> productDTOS = new LinkedList<>();
        for (ProductDAO productDAO : productRepository.findAll()) {
            productDTOS.add(map(productDAO));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAll(List<Long> ids) {
        final List<ProductDTO> productDTOS = new LinkedList<>();
        for (ProductDAO productDAO : productRepository.findAllById(ids)) {
            productDTOS.add(map(productDAO));
        }
        return productDTOS;
    }

    private static ProductDTO map(ProductDAO value) {
        return new ProductDTO(value.getId(), value.getName(), value.getCalories());
    }

    private static ProductDAO map(ProductDTO value) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.setName(value.getName());
        productDAO.setCalories(value.getCalories());
        return productDAO;
    }

}
