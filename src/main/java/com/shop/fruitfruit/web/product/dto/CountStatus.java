package com.shop.fruitfruit.web.product.dto;

import lombok.Data;

@Data
public class CountStatus {

    private int totalCount;
    private int onSaleCount;
    private int stopSaleCount;
    private int soldOutCount;
}
