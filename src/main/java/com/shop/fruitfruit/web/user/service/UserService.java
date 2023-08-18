package com.shop.fruitfruit.web.user.service;

import com.shop.fruitfruit.domain.user.User;
import com.shop.fruitfruit.web.user.dto.UserLoginForm;
import com.shop.fruitfruit.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User login(UserLoginForm form) {
        return userRepository.login(form);
    }

    public String join(User user, List<String> termTitle) {

        //비밀번호 인코딩
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //유저 조인
        userRepository.joinUser(user);

        //선택 약관 추가
        if (termTitle != null) {
            termTitle.forEach(title -> userRepository.joinUserTerm(user.getId(), title));
        }
        return user.getEmail();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public int updatePassword(String email, String password) {
        return userRepository.updatePassword(email, password);
    }
}
