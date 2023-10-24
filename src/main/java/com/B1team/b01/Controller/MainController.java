package com.B1team.b01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {


    @GetMapping("/main")
    public String main() {
        return "/main";
    }

    @GetMapping("/")
    public String mainRedirect() {
        return "redirect:/main";
    }

}
