package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface UserMapper {

    User login(UserLoginForm form);

    void joinUser(User user);

    void joinUserTerm(@Param("userId") Long userId, @Param("termTitle") String termTitle);
}
