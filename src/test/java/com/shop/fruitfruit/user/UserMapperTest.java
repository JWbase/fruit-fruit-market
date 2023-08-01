package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.user.User;
import com.shop.fruitfruit.web.user.UserLoginForm;
import com.shop.fruitfruit.web.user.UserMapper;
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
        form.setLoginId("leejw306@naver.com");
        form.setPassword("$2a$10$6AHLTJMSl44g58K0Lx.p9Ot1TpE8HlJCCsXawZvBqLY7P4Fd4xsQK");
        //when
        User user = userMapper.login(form);
        //then
        Assertions.assertThat(user.getEmail()).isEqualTo("leejw306@naver.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("$2a$10$6AHLTJMSl44g58K0Lx.p9Ot1TpE8HlJCCsXawZvBqLY7P4Fd4xsQK");
    }

}