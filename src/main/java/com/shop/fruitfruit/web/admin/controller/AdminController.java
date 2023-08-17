package com.shop.fruitfruit.web.admin.controller;

import com.shop.fruitfruit.domain.admin.Admin;
import com.shop.fruitfruit.web.admin.service.AdminService;
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
    public String login(@ModelAttribute("loginAdmin") Admin admin,
                        HttpServletRequest request) {

        Admin loginAdmin = adminService.findById(admin);
        log.info("세션!!!!!!!!!!!!!!!!!!!!!!!={}", loginAdmin);

        HttpSession session = request.getSession();
        session.setAttribute("loginAdmin", loginAdmin);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
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

    @GetMapping("/notification")
    public String notification() {
        return "admin/notification";
    }
}