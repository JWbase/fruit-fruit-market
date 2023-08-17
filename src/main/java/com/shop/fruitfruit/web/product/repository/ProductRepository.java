package com.shop.fruitfruit.web.product.repository;

import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import com.shop.fruitfruit.web.product.mapper.ProductMapper;
import com.shop.fruitfruit.web.product.dto.CountStatus;
import com.shop.fruitfruit.web.product.dto.ProductResponseDto;
import com.shop.fruitfruit.web.product.dto.ProductSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepository implements ProductMapper {

    private final ProductMapper productMapper;

    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
    }

    @Override
    public void addProductImage(ProductImage productImage) {
        productMapper.addProductImage(productImage);
    }

    @Override
    public List<ProductResponseDto> findProductAll(ProductSearchCond productSearch) {
        return productMapper.findProductAll(productSearch);
    }

    @Override
    public CountStatus countProductStatus() {
        return productMapper.countProductStatus();
    }

    @Override
    public int stopSaleProduct(String[] ids) {
        return productMapper.stopSaleProduct(ids);
    }

    @Override
    public Product findProductById(Long id) {
        return productMapper.findProductById(id);
    }
}
