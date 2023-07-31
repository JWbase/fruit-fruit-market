package com.shop.fruitfruit.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PasswordForm {

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.")
    private String password;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.")
    private String passwordConfirm;
}
