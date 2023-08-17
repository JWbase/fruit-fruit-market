package com.shop.fruitfruit.web.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
