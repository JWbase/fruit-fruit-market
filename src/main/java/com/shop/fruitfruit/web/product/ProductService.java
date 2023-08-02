package com.shop.fruitfruit.web.product;

import com.shop.fruitfruit.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements ProductMapper {

    private final ProductMapper productMapper;

    @Override
    public void addProduct(addProductForm addProductForm) {
        productMapper.addProduct(addProductForm);
    }
}
