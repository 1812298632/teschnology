package com.excel.pro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/page")
    public String page() {
        // back前面不写 斜杠
        System.out.println("into index ...");
        return "index";
    }
}
