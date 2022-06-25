package com.excel.pro.web;

import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    private UserDao userDao;

    @Resource
    private DepartDetailDao departDetailDao;

    @GetMapping("/")
    public String page() {
        // back前面不写 斜杠
        System.out.println("into index ...");
        return "index";
    }



}
