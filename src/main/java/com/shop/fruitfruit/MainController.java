package com.shop.fruitfruit;

import com.github.pagehelper.PageHelper;
import com.shop.fruitfruit.domain.admin.Admin;
import com.shop.fruitfruit.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String index(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                        Model model) {
        if (loginUser == null) {
            return "index";
        }

        model.addAttribute("user", loginUser);
        return "loginIndex";
    }

    /* admin page */
    @GetMapping("/admin")
    public String adminIndex(@SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin,
                             Model model) {

        if (loginAdmin == null) {
            model.addAttribute("admin", new Admin());
            return "adminIndex";
        }

        model.addAttribute("loginAdmin", loginAdmin);
        return "admin/dashboard";
    }

}
