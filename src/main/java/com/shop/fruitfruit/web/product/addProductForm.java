package com.shop.fruitfruit.web.product;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class addProductForm {

    @NotBlank(message = "상품명을 입력해주세요")
    private String name;

    private String categoryName;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @Range(min = 1, max = 100, message = "1 ~ 100 사이로 입력해주세요")
    private Integer discountRate;

    @NotNull(message = "재고를 입력해주세요")
    private Integer stockQuantity;

    @NotBlank(message = "상세정보를 입력해주세요")
    private String content;
}
