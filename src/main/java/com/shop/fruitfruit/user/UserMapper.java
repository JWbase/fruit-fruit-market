package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User login(UserLoginForm form);

    void joinUser(User user);

    void joinUserTerm(Long userId, String termStatus);
}
