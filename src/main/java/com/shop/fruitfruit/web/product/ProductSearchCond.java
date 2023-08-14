package com.shop.fruitfruit.web.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSearchCond {

    private String name;
    private List<Integer> status;
    private List<Integer> categoryId;
}
