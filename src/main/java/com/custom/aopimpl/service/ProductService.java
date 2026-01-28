package com.custom.aopimpl.service;

import com.custom.aopimpl.annotation.LogExecutionTime;
import com.custom.aopimpl.annotation.TrackErrors;
import com.custom.aopimpl.entity.Product;
import com.custom.aopimpl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @LogExecutionTime
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @LogExecutionTime
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @LogExecutionTime
    @TrackErrors
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("No product found with this ID"));
    }

    @LogExecutionTime
    @TrackErrors
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
