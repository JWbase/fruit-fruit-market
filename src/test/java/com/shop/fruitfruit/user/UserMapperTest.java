package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        //given
        UserLoginForm form = new UserLoginForm();
        form.setLoginId("user@naver.com");
        form.setPassword("1234");
        //when
        User user = userMapper.login(form);
        //then
        Assertions.assertThat(user.getEmail()).isEqualTo("user@naver.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
    }

}