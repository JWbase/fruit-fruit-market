package com.shop.fruitfruit.domain.product;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductImage {
    private Long id;
    private Long productId;
    private String fileName;
    private String filePath;
    private String url;
    private Integer type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
