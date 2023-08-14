package com.shop.fruitfruit.web.user;

import com.shop.fruitfruit.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository implements UserMapper {

    private final UserMapper userMapper;

    @Override
    public User login(UserLoginForm form) {
        return userMapper.login(form);
    }

    @Override
    public void joinUser(User user) {
        userMapper.joinUser(user);
    }

    @Override
    public void joinUserTerm(Long userId, String termTitle) {
        userMapper.joinUserTerm(userId, termTitle);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userMapper.existsByNickname(nickname);
    }

    @Override
    public int updatePassword(String email, String password) {
        return userMapper.updatePassword(email, password);
    }
}
