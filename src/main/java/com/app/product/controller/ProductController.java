package com.app.product.controller;

import com.app.product.model.Product;
import com.app.product.model.Tags;
import com.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @PostMapping("/product/{productName}")
    public String createProduct(@PathVariable String productName, @RequestBody Product product) {
        return productService.createProduct(productName, product);
    }

    @GetMapping("/product/{productName}")
    public Product getProduct(@PathVariable String productName) {
        return productService.getProduct(productName);
    }

    @PutMapping("/product/{productName}")
    public String updateProduct(@PathVariable String productName, @RequestBody Product product) {
        return productService.updateProduct(productName, product);
    }

    @DeleteMapping("/product/{productName}")
    public String deleteProduct(@PathVariable String productName) {
        return productService.deleteProduct(productName);
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return productService.getProducts(pageNumber, pageSize);
    }

    @GetMapping("/searchProductByTag")
    public List<Product> searchProductByTag(@RequestParam String inputTag) {
        return productService.searchProductByTag(inputTag);
    }

    @GetMapping("/getProductDistributionByTag")
    public List<Tags> getProductDistributionByTag() {
        return productService.getProductDistributionByTag();
    }
}
