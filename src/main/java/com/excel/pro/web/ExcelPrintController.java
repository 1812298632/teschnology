package com.excel.pro.web;

import com.excel.pro.dao.impl.DepartDetailDao;
import com.excel.pro.dao.impl.UserDao;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.SysUser;
import com.excel.pro.service.DepartService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ExcelPrintController {
    @Resource
    private UserDao userDao;

    @Resource
    private DepartDetailDao departDetailDao;

    @Resource
    private DepartService departService;

    //http://127.0.0.1:9080/hello RestController
    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @GetMapping("/query")
    public String queryUser() {
        List<SysUser> sysUsers = userDao.selectList(null);
        return "test";
    }

    @GetMapping("/excel")
    @ResponseBody
    public String insertDepartDetail() throws IOException {
        LinkedList<String> sheetNameList = new LinkedList<>();
        sheetNameList.add("解放车3");
        sheetNameList.add("解放车4");
        sheetNameList.add("解放车5");
        sheetNameList.add("解放车6");
        sheetNameList.add("解放车7");
        sheetNameList.add("解放车8");
        sheetNameList.add("解放车9");
        for (String s : sheetNameList) {
            departService.insertDepartDetail(s);

        }

        return "123";
    }
}
