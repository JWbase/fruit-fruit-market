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

    @Transactional
    public String join(User user, List<String> termStatus) {
        //유저
        userMapper.joinUser(user);
        //약관
        if (termStatus != null) {
            for (String status : termStatus) {
                userMapper.joinUserTerm(user.getId(), status);
            }
        }

        return user.getEmail();
    }

    @Override
    public void joinUser(User user) {
    }

    @Override
    public void joinUserTerm(Long userId, String termStatus) {
    }
}
