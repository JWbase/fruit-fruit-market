package com.shop.fruitfruit.web.user.mapper;

import com.shop.fruitfruit.domain.user.User;
import com.shop.fruitfruit.web.user.dto.UserLoginForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    //로그인
    User login(UserLoginForm form);

    //회원가입 (유저 정보)
    void joinUser(User user);

    //회원가입 (약관 정보)
    void joinUserTerm(@Param("userId") Long userId, @Param("termTitle") String termTitle);

    //email 중복 체크
    boolean existsByEmail(@Param("email") String email);

    //nickname 중복 체크
    boolean existsByNickname(@Param("nickname") String nickname);

    //비밀번호 변경
    int updatePassword(@Param("email") String email, @Param("password") String password);

}
