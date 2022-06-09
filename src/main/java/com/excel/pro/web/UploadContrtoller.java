package com.excel.pro.web;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadContrtoller {


    /**
     * 上传文件之后直接解析
     * @param file
     * @throws IOException
     */
    @RequestMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        FileOutputStream out = null;
        String fileName = file.getOriginalFilename();
        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());

        HSSFSheet sheet = workbook.getSheet("解放车毛利");

        System.out.println(111);
    }


    /**
     * 上传文件并将文件下载到本地
     *
     * @param file
     */
    public void uploadAndDown(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        FileOutputStream out = null;
        try {
            String fileName = file.getOriginalFilename();
            if (fileName.indexOf("\\") != -1) {
                fileName = fileName.substring(fileName.lastIndexOf("\\"));
            }
            // 获取文件存放地址
            String filePath = "D:/element-UI/test/";
            File f = new File(filePath);
            if (!f.exists()) {
                f.mkdirs();// 不存在路径则进行创建
            }
            // 重新自定义文件的名称
            filePath = filePath + fileName;
            out = new FileOutputStream(filePath);
            out.write(file.getBytes());
            out.flush();
            out.close();
            map.put("1", "ok");
            Thread.sleep(10000);

        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
