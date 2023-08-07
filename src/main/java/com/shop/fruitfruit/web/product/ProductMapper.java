package com.shop.fruitfruit.web.product;

import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    void addProduct(@Param("form") Product product);

    void addProductImage(@Param("image") ProductImage productImage);
}
