package com.shop.fruitfruit.web.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.fruitfruit.domain.product.Product;
import com.shop.fruitfruit.domain.product.ProductImage;
import com.shop.fruitfruit.web.firebase.FireBaseService;
import com.shop.fruitfruit.web.product.dto.CountStatus;
import com.shop.fruitfruit.web.product.dto.ProductResponseDto;
import com.shop.fruitfruit.web.product.dto.ProductSearchCond;
import com.shop.fruitfruit.web.product.dto.UploadType;
import com.shop.fruitfruit.web.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FireBaseService fireBaseService;

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

    // 상품 판매 중지
    public int changeStatusProduct(List<Long> ids, int status) {
        return productRepository.changeStatusProduct(ids, status);
    }

    public Product findProductById(Long id) {
        return productRepository.findProductById(id);
    }

    public List<UploadType> uploadImagesAndThumbnail(List<MultipartFile> files, List<MultipartFile> thumbnails) {
        List<UploadType> uploadTypes = new ArrayList<>();
        if (files != null) {
            files.forEach(file -> uploadTypes.add(new UploadType(file, "images", 0)));
        }
        thumbnails.forEach(thumbnail -> uploadTypes.add(new UploadType(thumbnail, "thumbnail", 1)));

        return uploadTypes;
    }

    public void addProductWithImages(Product product, List<UploadType> uploadTypes) throws IOException {
        String firebaseContent = product.getContent();
        List<ProductImage> images = new ArrayList<>();
        Pattern imgPattern = Pattern.compile("<img src=\\\"(blob:[^\\\"]+)\\\"[^>]*>");

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

        product.setContent(firebaseContent);
        product.setImages(images);

        addProduct(product);
    }
}
