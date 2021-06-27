package com.app.product.service;

import com.app.product.model.Product;
import com.app.product.model.Tags;
import com.app.product.repository.ProductRepository;
import com.app.product.repository.TagsRepository;
import com.app.product.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private Utility utility;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TagsRepository tagsRepository;

    /**
     * @param productName
     * @param product
     * @return a string on successful creation/already exists
     */
    public String createProduct(String productName, Product product) {
        Integer quantity = product.getQuantity();
        Double price = product.getPrice();
        List<String> tags = product.getTags();

        utility.makeTagsLowerCase(tags);

        List<Product> productList = productRepository.findByProductName(productName);

        if (productList == null || productList.isEmpty()) // new product
        {
            Product productItem = new Product();
            productItem.setQuantity(quantity);
            productItem.setPrice(price);
            updateTagsTable(tags);
            productItem.setTags(tags);
            productItem.setProductName(productName);
            productRepository.save(productItem);
            return "Product " + productName + " successfully added";
        } else {
            return "Product " + productName + " already exists";
        }
    }

    /**
     * @param tags
     */
    private void updateTagsTable(List<String> tags) {
        for (String tag : tags) {
            List<Tags> tagsList = tagsRepository.findByTag(tag);
            Tags tagItem = new Tags();
            if (tagsList == null || tagsList.isEmpty()) {
                tagItem.setTag(tag);
                tagItem.setCount(1);
            } else {
                tagItem = tagsList.get(0);
                tagItem.setCount(tagItem.getCount() + 1);
            }
            tagsRepository.save(tagItem);
        }
    }

    /**
     * @param productName
     * @return a product entity by productName
     */
    public Product getProduct(String productName) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return null;
        } else {
            return productList.get(0);
        }
    }

    /**
     * @param productName
     * @param product
     * @return a string on successful update/does not exists
     */
    public String updateProduct(String productName, Product product) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return "Product " + productName + " does not exist";
        } else {
            Integer quantity = product.getQuantity();
            Double price = product.getPrice();
            List<String> tags = product.getTags();
            utility.makeTagsLowerCase(tags);
            Product productItem = productList.get(0);
            productItem.setQuantity(quantity);
            productItem.setPrice(price);
            List<String> currentTags = productItem.getTags();
            deleteOldTags(currentTags);
            productItem.setTags(tags);
            updateTagsTable(tags);
            productRepository.save(productItem);
            return "Product " + productName + " successfully updated";
        }
    }

    /**
     * @param tags
     */
    private void deleteOldTags(List<String> tags) {
        for (String tag : tags) {
            List<Tags> tagsList = tagsRepository.findByTag(tag);
            Tags tagItem = tagsList.get(0);
            tagItem.setCount(tagItem.getCount() - 1);
            tagsRepository.save(tagItem);
        }
    }

    /**
     * @param productName
     * @return a string on successful deletion/does not exists
     */
    public String deleteProduct(String productName) {
        List<Product> productList = productRepository.findByProductName(productName);
        if (productList == null || productList.isEmpty()) // product does not exist
        {
            return "Product " + productName + " does not exist";
        } else {
            Product productItem = productList.get(0);
            if (productList.get(0).getTags() != null && !productList.get(0).getTags().isEmpty())
                deleteOldTags(productList.get(0).getTags());
            productRepository.deleteByProductName(productItem.getProductName());
            return "Product " + productName + " successfully deleted";
        }
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return list of products by pagination
     */
    public List<Product> getProducts(int pageNumber, int pageSize) {
               /*int skipCount = (pageNumber - 1) * pageSize;
            List<Product> result = new ArrayList<>();
            int count = 0;
            for (int i = skipCount; i < productList.size() && count < pageSize; i++) {
                result.add(productList.get(i));
                count++;
            }*/
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Product> result = productRepository.findAll(paging);
        return result.getContent();
    }

    /**
     * @param inputTag
     * @return list of products by inputTag
     */
    public List<Product> searchProductByTag(String inputTag) {
        List<String> tagList = new ArrayList<>();
        tagList.add(inputTag.toLowerCase());
        return productRepository.findByTagsIn(tagList);
    }

    /**
     * @return product distribution by tag
     */
    public List<Tags> getProductDistributionByTag() {
        return tagsRepository.findByCountGreaterThan(0);
    }
}
