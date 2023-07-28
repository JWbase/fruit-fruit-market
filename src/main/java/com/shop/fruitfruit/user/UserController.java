package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("user") User user) {
        return "user/join";
    }

    //회원가입
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") User user,
                        BindingResult bindingResult,
                        @RequestParam(required = false) List<String> termStatus,
                        RedirectAttributes redirectAttributes,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {

        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        //비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        String joinEmail = userService.join(user, termStatus);
        redirectAttributes.addAttribute("email", joinEmail);

        return "redirect:/user/joinConfirm?email={email}";
    }

    //회원가입 성공 페이지
    @GetMapping("/joinConfirm")
    public String joinConfirm(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "user/joinConfirm";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute UserLoginForm form) {
        return "user/login";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute UserLoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        User loginUser = userService.login(form);

        if (loginUser == null) {
            bindingResult.reject("loginFail", "이메일과 비밀번호가 일치하지 않습니다.");
            return "user/login";
        }

        //로그인 성공 처리
        //세션이 있으면 세션을 반환하고 없으면 신규 세션을 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원정보를 보관
        session.setAttribute("Login_User", loginUser);

        return "redirect:/";
    }

    @GetMapping("{pageName}")
    public String pageName(@PathVariable String pageName) {
        return pageName;
    }
}