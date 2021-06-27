package com.app.product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto extends LoginDto{
    private String city;
    //add other fields needed while signup if any
}
