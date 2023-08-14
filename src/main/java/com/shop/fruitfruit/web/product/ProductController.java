package com.shop.fruitfruit.web.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import com.shop.fruitfruit.web.firebase.FireBaseService;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final FireBaseService fireBaseService;

    //상품관리 폼
    @GetMapping("/admin/product")
    public String product(@ModelAttribute("productSearch") ProductSearchCond productSearch,
                          @RequestParam(required = false, defaultValue = "1") int pageNum,
                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                          Model model) {

        log.info("로그입니다!!!!!!!!!!!!!!!!!!! ={}", productSearch);
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
                                        @RequestParam("file") List<MultipartFile> file) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        String firebaseContent = null;
        List<ProductImage> images = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String url = fireBaseService.uploadFiles(multipartFile, "images", multipartFile.getOriginalFilename());
            firebaseContent = form.getContent().replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + url + "\" />");

            ProductImage image = ProductImage.builder()
                    .filePath("images")
                    .fileName(multipartFile.getOriginalFilename())
                    .url(url)
                    .build();
            images.add(image);
        }
        form.setContent(firebaseContent);
        form.setImages(images);
        productService.addProduct(form);

        return ResponseEntity.ok("true");
    }
}