package com.shop.fruitfruit.web.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProductResponseDto {

    private Long id;
    private Integer status;
    private String categoryName;
    private String name;
    private Integer price;
    private Integer discountRate;
    private Integer likeCount;
    private Integer reviewCount;
    private Integer stockQuantity;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
