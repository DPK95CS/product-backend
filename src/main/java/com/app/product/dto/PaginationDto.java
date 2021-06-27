package com.app.product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private Integer pageNumber;
    private Integer pageSize;
}
