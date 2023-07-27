package com.shop.fruitfruit.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String password;

    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
