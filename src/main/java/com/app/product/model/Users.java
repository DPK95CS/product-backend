package com.app.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Users {
    @Id
    private String id;
    private String email;
    private String password;
    private String city;
}
