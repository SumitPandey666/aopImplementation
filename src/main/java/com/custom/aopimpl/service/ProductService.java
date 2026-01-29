package com.custom.aopimpl.service;

import com.custom.aopimpl.annotation.AuditOperation;
import com.custom.aopimpl.annotation.LogExecutionTime;
import com.custom.aopimpl.annotation.Retry;
import com.custom.aopimpl.annotation.TrackErrors;
import com.custom.aopimpl.entity.Product;
import com.custom.aopimpl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @LogExecutionTime
    @AuditOperation("Create New Product")
    @Retry
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @LogExecutionTime
    @Retry
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id") //Spring's AOP
    @LogExecutionTime
    @TrackErrors
    @Retry
    public Product getProductById(Long id){
        simulateSlowService(); //artifical delay to prove our caching works
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("No product found with this ID"));
    }

    @LogExecutionTime
    @TrackErrors
    @Retry
    @AuditOperation("Delete Product")
    @CacheEvict(value = "products", key= "#id")
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    private void simulateSlowService(){
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
