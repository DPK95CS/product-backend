package com.app.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductTagMapping {
    @Id
    private String id;
    private String productName;
    private String tag;
}
