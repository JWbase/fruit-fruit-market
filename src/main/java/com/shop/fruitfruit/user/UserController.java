package com.shop.fruitfruit.user;

import com.shop.fruitfruit.domain.user.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //회원가입 페이지
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("user") User user) {
        return "user/join";
    }

    //회원가입
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("termTitle") List<String> termTitle,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        //비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (userService.existsByEmail(user.getEmail()))
            bindingResult.rejectValue("email", "duplicate.email", "이미 가입된 계정 입니다.");

        if (userService.existsByNickname(user.getNickname()))
            bindingResult.rejectValue("nickname", "duplicate.nickname", "해당 닉네임은 이미 사용중입니다.");

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("user", user);
            return "user/join";
        }

        String joinEmail = userService.join(user, termTitle);
        redirectAttributes.addAttribute("email", joinEmail);

        return "redirect:/user/joinConfirm?email={email}";
    }

    //회원가입 성공 페이지
    @GetMapping("/joinConfirm")
    public String joinConfirm(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "user/joinConfirm";
    }

    //로그인 페이지
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
            log.info("errors={}", bindingResult);
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
        session.setAttribute("loginUser", loginUser);


        return "redirect:/";
    }

    //패스워드 찾기 화면
    @GetMapping("/findPassword")
    public String findPassword() {
        return "user/findPw";
    }

    //패스워드 찾기
    @PostMapping("/findPassword")
    @ResponseBody
    public Map<String, Object> findPassword(@RequestBody Map<String, String> email) {
        boolean emailExists = userService.existsByEmail(email.get("email"));

        Map<String, Object> response = new HashMap<>();
        response.put("exists", emailExists);

        return response;
    }

    //비밀번호 변경 화면
    @GetMapping("/changePassword")
    public String changePassword() {
        return "user/changePw";
    }

    //비밀번호 변경
    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody Map<String, String> requestMap) {

        int passwordChanged = userService.updatePassword(requestMap.get("email"), requestMap.get("password"));
        return passwordChanged > 0 ? "true" : "false";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}