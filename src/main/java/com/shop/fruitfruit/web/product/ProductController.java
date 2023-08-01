package com.shop.fruitfruit.web.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    //상품관리
    @GetMapping("/admin/product")
    public String product() {
        return "products/product";
    }

    //상품등록
    @GetMapping("/admin/addProduct")
    public String addProduct() {
        return "products/addProduct";
    }
}
