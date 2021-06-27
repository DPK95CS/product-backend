package com.app.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class Tags {
    @Id
    private String id;
    private String tag;
    private Integer count;
}
