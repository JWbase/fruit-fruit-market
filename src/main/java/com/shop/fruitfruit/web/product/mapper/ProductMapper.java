package com.shop.fruitfruit.web.product.mapper;

import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import com.shop.fruitfruit.web.product.dto.CountStatus;
import com.shop.fruitfruit.web.product.dto.ProductResponseDto;
import com.shop.fruitfruit.web.product.dto.ProductSearchCond;
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
    int changeStatusProduct(@Param("ids") List<Long> ids, @Param("status") int status);

    //상품 조회
    Product findProductById(Long id);

}
