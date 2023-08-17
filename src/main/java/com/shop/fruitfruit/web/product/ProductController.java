package com.shop.fruitfruit.web.product;

import com.github.pagehelper.PageInfo;
import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import com.shop.fruitfruit.web.firebase.FireBaseService;
import lombok.Getter;
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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final FireBaseService fireBaseService;


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
                                        @RequestParam("file") List<MultipartFile> files,
                                        @RequestParam("thumbnail") List<MultipartFile> thumbnails) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        String firebaseContent = form.getContent();
        List<ProductImage> images = new ArrayList<>();
        Pattern imgPattern = Pattern.compile("<img src=\\\"(blob:[^\\\"]+)\\\"[^>]*>");

        // 이미지와 썸네일 리스트에 추가
        List<UploadType> uploadTypes = new ArrayList<>();
        files.forEach(file -> uploadTypes.add(new UploadType(file, "images", 0)));
        thumbnails.forEach(thumbnail -> uploadTypes.add(new UploadType(thumbnail, "thumbnail", 1)));

        for (UploadType uploadType : uploadTypes) {
            MultipartFile multipartFile = uploadType.getFile();
            String url = fireBaseService.uploadFiles(multipartFile, uploadType.getPath(), multipartFile.getOriginalFilename());

            if (uploadType.getType() == 0) {
                Matcher imgMatcher = imgPattern.matcher(firebaseContent);
                if (imgMatcher.find()) {
                    firebaseContent = imgMatcher.replaceFirst("<img src=\"" + url + "\" />");
                }
            }

            ProductImage image = ProductImage.builder()
                    .filePath(uploadType.getPath())
                    .fileName(multipartFile.getOriginalFilename())
                    .url(url)
                    .type(uploadType.getType())
                    .build();
            images.add(image);
        }

        form.setContent(firebaseContent);
        form.setImages(images);
        productService.addProduct(form);

        return ResponseEntity.ok("true");
    }

    @RequiredArgsConstructor
    @Getter
    static class UploadType {
        private final MultipartFile file;
        private final String path;
        private final int type;
    }

}