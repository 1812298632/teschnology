package com.excel.pro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ExcelPrintController {

    //http://127.0.0.1:9080/hello RestController
    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }

}
