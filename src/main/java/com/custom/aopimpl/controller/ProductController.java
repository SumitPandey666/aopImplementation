package com.custom.aopimpl.controller;

import com.custom.aopimpl.entity.Product;
import com.custom.aopimpl.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductByID(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/id")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
    }


}
