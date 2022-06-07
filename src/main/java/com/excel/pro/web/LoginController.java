package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.UserDao;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.SysUser;
import com.excel.pro.service.DepartService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @RequestMapping("/myquery")
    @ResponseBody
    public List<Departdetail> queryUser(HttpServletRequest request) {

        QueryWrapper<Departdetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(Departdetail::getSheetid);
        List<Departdetail> departdetails = departDetailDao.selectList(queryWrapper);
        return departdetails;
    }
}
