package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.UserDao;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.QueryEneity;
import com.excel.pro.entity.SysUser;
import com.excel.pro.service.DepartService;
import com.excel.pro.util.RequestUtil;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    private UserDao userDao;

    @Resource
    private DepartDetailDao departDetailDao;

    @GetMapping("/page")
    public String page() {
        // back前面不写 斜杠
        System.out.println("into index ...");
        return "index";
    }



}
