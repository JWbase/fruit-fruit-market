package com.shop.fruitfruit.domain.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Product {
    private Long id;

    private Long categoryId;

    @NotBlank(message = "상품명을 입력해주세요")
    private String name;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @Range(min = 1, max = 100, message = "1 ~ 100 사이로 입력해주세요")
    private Integer discountRate;

    private Integer status;

    @NotNull(message = "재고를 입력해주세요")
    private int stockQuantity;

    @NotBlank(message = "상세정보를 입력해주세요")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ProductImage> images;
}

