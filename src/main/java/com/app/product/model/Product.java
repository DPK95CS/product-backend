package com.app.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String productName;
    private Integer quantity;
    private Double price;
    private List<String> tags;
}
