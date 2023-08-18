package com.shop.fruitfruit.web.product.controller;

import com.github.pagehelper.PageInfo;
import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.web.product.dto.CountStatus;
import com.shop.fruitfruit.web.product.dto.ProductResponseDto;
import com.shop.fruitfruit.web.product.dto.ProductSearchCond;
import com.shop.fruitfruit.web.product.dto.UploadType;
import com.shop.fruitfruit.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    //판매 상태 변경
    @PostMapping("/admin/changeStatus")
    @ResponseBody
    public String stopSale(@RequestBody Map<String, Object> data) {

        List<Long> ids = (List<Long>) data.get("ids");
        int status = (int) data.get("status");

        int updateRows = productService.changeStatusProduct(ids, status);
        if (updateRows > 0) {
            return "true";
        }
        return "false";
    }

    //상품수정 폼
    @GetMapping("/admin/product/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "products/editProduct";
    }

    //상품수정
    @PostMapping("/admin/product/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Validated @ModelAttribute("product") Product form,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "products/editProduct";
        }

        return "redirect:/admin/product";
    }

    //상품관리 폼
    @GetMapping("/admin/product")
    public String product(@ModelAttribute("productSearch") ProductSearchCond productSearch,
                          @RequestParam(required = false, defaultValue = "1") int pageNum,
                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                          Model model) {

        PageInfo<ProductResponseDto> products = productService.ProductFindAll(productSearch, pageNum, pageSize);
        CountStatus countStatus = productService.countProductStatus();

        model.addAttribute("countStatus", countStatus);
        model.addAttribute("products", products);
        return "products/product";
    }

    //상품등록 폼
    @GetMapping("/admin/addProduct")
    public String addProduct(@ModelAttribute("form") Product form) {
        return "products/addProduct";
    }

    @PostMapping("/admin/addProduct")
    @ResponseBody
    public ResponseEntity<?> addProduct(@Validated @ModelAttribute("form") Product form,
                                        BindingResult bindingResult,
                                        @RequestParam(value = "file", required = false) List<MultipartFile> files,
                                        @RequestParam("thumbnail") List<MultipartFile> thumbnails) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        List<UploadType> uploadTypes = productService.uploadImagesAndThumbnail(files, thumbnails);
        productService.addProductWithImages(form, uploadTypes);

        return ResponseEntity.ok("true");
    }

}