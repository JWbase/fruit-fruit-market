package com.shop.fruitfruit.web.product.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class UploadType {

    private final MultipartFile file;
    private final String path;
    private final int type;
}
