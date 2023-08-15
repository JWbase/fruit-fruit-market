package com.shop.fruitfruit.web.product;

import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void addProduct(@Param("form") Product product);

    void addProductImage(@Param("image") ProductImage productImage);

    List<ProductResponseDto> findProductAll(@Param("productSearch") ProductSearchCond productSearch);

    //판매상태에 따른 상품 수 출력
    CountStatus countProductStatus();

    //판매 중지
    int stopSaleProduct(String[] ids);

}
