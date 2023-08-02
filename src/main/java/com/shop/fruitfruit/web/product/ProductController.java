package com.shop.fruitfruit.web.product;

import com.shop.fruitfruit.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    //상품관리 폼
    @GetMapping("/admin/product")
    public String product() {
        return "products/product";
    }

    //상품등록 폼
    @GetMapping("/admin/addProduct")
    public String addProduct(@ModelAttribute("form") addProductForm form) {
        return "products/addProduct";
    }

    @PostMapping("/admin/addProduct")
    public String addProduct(@Validated @ModelAttribute("form") addProductForm form,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("form", form);
            return "products/addProduct";
        }

        productService.addProduct(form);
        return "products/product";
    }
}