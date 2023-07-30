package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserMapper {

    private final UserMapper userMapper;

    @Override
    public User login(UserLoginForm form) {
        return userMapper.login(form);
    }

    public String join(User user, List<String> termTitle) {

        //유저 조인
        userMapper.joinUser(user);

        //선택 약관 추가
        if (termTitle != null) {
            termTitle.forEach(title -> userMapper.joinUserTerm(user.getId(), title));
        }

        return user.getEmail();
    }

    //email 및 nickname 중복체크


    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userMapper.existsByNickname(nickname);
    }

    @Override
    public void joinUser(User user) {
    }

    @Override
    public void joinUserTerm(Long userId, String termTitle) {
    }

}
