package com.shop.fruitfruit.web.admin;

import com.shop.fruitfruit.domain.admin.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public String login(@ModelAttribute Admin admin, HttpServletRequest request) {

        Admin loginAdmin = adminService.findById(admin);

        HttpSession session = request.getSession();
        session.setAttribute("loginAdmin", loginAdmin);

        return "redirect:/admin";
    }

    @GetMapping("/banner")
    public String banner() {
        return "admin/banner";
    }

    @GetMapping("/review")
    public String review() {
        return "admin/review";
    }

    @GetMapping("/user")
    public String user() {
        return "admin/user";
    }

    @GetMapping("notification")
    public String notification() {
        return "admin/notification";
    }
}
