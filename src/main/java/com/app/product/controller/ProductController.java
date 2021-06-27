package com.app.product.controller;

import com.app.product.dto.PaginationDto;
import com.app.product.model.Product;
import com.app.product.model.ProductTagMapping;
import com.app.product.repository.ProductRepository;
import com.app.product.repository.ProductTagMappingRepository;
import com.app.product.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ValidationUtility validationUtility;
    @Autowired
    private ProductTagMappingRepository productTagMappingRepository;

    @PostMapping("/product/{productName}")
    public String createProduct(@PathVariable String productName, @RequestBody Product product) {
        Integer quantity = product.getQuantity();
        Double price = product.getPrice();
        List<String> tags = product.getTags();
        makeTagsLowerCase(tags);

        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // new product
        {
            Product productItem = new Product();
            productItem.setQuantity(quantity);
            productItem.setPrice(price);
            productItem.setTags(tags);
            productItem.setProductName(productName);
            saveProductTagMapping(productName, tags);
            productRepository.save(productItem);
            return "Product " + productName + " successfully added";
        } else {
            return "Product " + productName + " already exists";
        }
    }

    private void saveProductTagMapping(String productName, List<String> tags) {
        for (String tag : tags) {
            List<ProductTagMapping> productTagMappings = productTagMappingRepository.findByProductNameAndTag(productName, tag);
            if (productTagMappings == null || productTagMappings.isEmpty()) {
                ProductTagMapping productTagMapping = new ProductTagMapping();
                productTagMapping.setTag(tag);
                productTagMapping.setProductName(productName);
                productTagMappingRepository.save(productTagMapping);
            }
        }

    }

    @GetMapping("/product/{productName}")
    public Product getProduct(@PathVariable String productName) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return null;
        } else {
            return productList.get(0);
        }
    }

    @PutMapping("/product/{productName}")
    public String updateProduct(@PathVariable String productName, @RequestBody Product product) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return "Product " + productName + " does not exist";
        } else {
            Integer quantity = product.getQuantity();
            Double price = product.getPrice();
            List<String> tags = product.getTags();
            makeTagsLowerCase(tags);
            Product productItem = productList.get(0);
            productItem.setQuantity(quantity);
            productItem.setPrice(price);
            productItem.setTags(tags);
            deleteOldTags(productName);
            saveProductTagMapping(productName, tags);
            productRepository.save(productItem);
            return "Product " + productName + " successfully updated";
        }
    }

    private void deleteOldTags(String productName) {
        productTagMappingRepository.deleteByProductName(productName);
    }

    @DeleteMapping("/product/{productName}")
    public String deleteProduct(@PathVariable String productName) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return "Product " + productName + " does not exist";
        } else {
            Product productItem = productList.get(0);
            productRepository.deleteByProductName(productItem.getProductName());
            productTagMappingRepository.deleteByProductName(productItem.getProductName());
            return "Product " + productName + " successfully deleted";
        }
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestBody PaginationDto paginationDto) {
        List<Product> productList = productRepository.findAll();
        if (productList == null || productList.isEmpty())
            return null;
        else {
            int pageNumber = paginationDto.getPageNumber();
            int pageSize = paginationDto.getPageSize();
            int skipCount = (pageNumber - 1) * pageSize;
            List<Product> result = new ArrayList<>();
            int count = 0;
            for (int i = skipCount; i < productList.size() && count < pageSize; i++) {
                result.add(productList.get(i));
                count++;
            }
            return result;
        }
    }

    @GetMapping("/searchProductByTag")
    public List<String> searchProductByTag(@RequestParam String inputTag) {
        List<String> products = new ArrayList<>();
        List<ProductTagMapping> productTagMappings = productTagMappingRepository.findByTag(inputTag.toLowerCase());
        for (ProductTagMapping productTagMapping : productTagMappings) {
            products.add(productTagMapping.getProductName());
        }
        return products;
    }

    private void makeTagsLowerCase(List<String> tags) {
        for (int i = 0; i < tags.size(); i++)
            tags.set(i, tags.get(i).toLowerCase());
    }

    @GetMapping("/getProductDistributionByTag")
    public Map<String, List<String>> getProductDistributionByTag() {
        Map<String, List<String>> result = new HashMap<>();
        List<Product> productList = productRepository.findAll();
        if (productList == null || productList.isEmpty())
            return null;
        else {
            for (Product product : productList) {
                List<ProductTagMapping> productTagMappings = productTagMappingRepository.findByProductName(product.getProductName());
                for (ProductTagMapping productTagMapping : productTagMappings) {
                    String tag = productTagMapping.getTag();
                    List<String> products;
                    if (result.containsKey(tag) == false || result.get(tag) == null) {
                        products = new ArrayList<>();
                    } else {
                        products = result.get(tag);
                    }
                    products.add(product.getProductName());
                    result.put(tag, products);
                }
            }
        }
        return result;
    }

}
