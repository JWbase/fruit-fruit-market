package com.shop.fruitfruit;

import com.shop.fruitfruit.domain.admin.Admin;
import com.shop.fruitfruit.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
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
    public String adminIndex(@SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin, Model model) {

        if (loginAdmin == null) {
            return "adminIndex";
        }

        model.addAttribute("admin", loginAdmin);
        return "admin/dashboard";
    }

}
