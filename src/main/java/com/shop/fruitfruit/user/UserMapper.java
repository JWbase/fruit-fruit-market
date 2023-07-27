package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User login(UserLoginForm form);
}
