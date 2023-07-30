package com.shop.fruitfruit.domain;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    @NotBlank(message = "이메일 주소를 입력하세요.")
    @Email(message = "아이디는 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, message = "닉네임은 2자이상 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.")
    private String password;

    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
