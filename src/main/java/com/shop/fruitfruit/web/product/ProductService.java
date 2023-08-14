package com.shop.fruitfruit.web.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(Product product) {
        //상품등록
        productRepository.addProduct(product);

        //이미지 등록
        List<ProductImage> images = product.getImages();
        images.forEach(image -> {
            image.setProductId(product.getId());
            productRepository.addProductImage(image);
        });
    }

    // 상품 전체 조회
    public PageInfo<ProductResponseDto> ProductFindAll(ProductSearchCond productSearch, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ProductResponseDto> products = new PageInfo<>(productRepository.findProductAll(productSearch));
        products.setNavigatePages(10);
        return products;
    }

    // 상품 상태 조회
    public CountStatus countProductStatus() {
        return productRepository.countProductStatus();
    }
}
