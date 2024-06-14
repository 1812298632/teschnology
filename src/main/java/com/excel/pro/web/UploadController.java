package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.IncomeStatementDao;
import com.excel.pro.entity.*;
import com.excel.pro.service.DepartService;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.service.UploadService;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController {
    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    private DepartService departService;

    @Resource
    private DepartDetailDao departDetailDao;

    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private IncomeStatementDao incomeStatementDao;

    @Resource
    private UploadService uploadService;

    /**
     * 上传文件之后直接解析
     *
     * @param file
     * @throws IOException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();


        Map<String, Object> map = new HashMap<>();
        FileOutputStream out = null;
        String fileName = file.getOriginalFilename();
        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        ConstantUtil.departUploadEntity.setExcelname(fileName);

        /*HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());

        HSSFSheet sheet = workbook.getSheet("解放车毛利");*/
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheet(ConstantUtil.departUploadEntity.getSheetname());
        departService.insertDepartDeatilByUpload(sheet);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("上传成功");

        return responseEntity;
    }

    @RequestMapping("/uploadall")
    @ResponseBody
    public ResponseEntity uploadall(@RequestParam("file") MultipartFile file) throws IOException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ResponseEntity responseEntity = new ResponseEntity();


        Map<String, Object> map = new HashMap<>();
        FileOutputStream out = null;
        String fileName = file.getOriginalFilename();
        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        ConstantUtil.departUploadEntity.setExcelname(fileName);

        /*HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());

        HSSFSheet sheet = workbook.getSheet("解放车毛利");*/
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheet("干线明细");
        //XSSFSheet sheet = workbook.getSheet("sheet");
        //XSSFSheet sheet = workbook.getSheet("基础数据");

        //2022 2023
        //uploadService.uploadNewCar(sheet);

        //uploadService.uploadSystemSet(sheet);


        uploadService.uploadMainline(sheet);

        //uploadService.uploadCarCount(sheet);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("上传成功");

        return responseEntity;
    }



    @RequestMapping("/incomeUpload")
    @ResponseBody
    public ResponseEntity incomeUpload(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();


        Map<String, Object> map = new HashMap<>();
        FileOutputStream out = null;
        String fileName = file.getOriginalFilename();
        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        ConstantUtil.incomeUploadEntity.setExcelname(fileName);


        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheet(ConstantUtil.incomeUploadEntity.getSheetname());
        incomeStatementService.insertIncomeStatementByUpload(sheet);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("上传成功");

        return responseEntity;
    }


    /**
     * 台账上传时的参数
     *
     * @param request
     * @return
     */
    @RequestMapping("setParamter")
    @ResponseBody
    public ResponseEntity setUploadParam(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        DepartUploadEntity departUploadEntity = ConstantUtil.departUploadEntity;
        String requestParam = RequestUtil.getJsonObjectData(request);


        try {
            departUploadEntity.setType(RequestUtil.getObjectValue(requestParam, "type"));
            departUploadEntity.setYear(RequestUtil.getObjectValue(requestParam, "year"));
            departUploadEntity.setCartype(RequestUtil.getObjectValue(requestParam, "cartype"));
            departUploadEntity.setMonth(RequestUtil.getObjectValue(requestParam, "month"));
            departUploadEntity.setSheetname(RequestUtil.getObjectValue(requestParam, "sheetname"));
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("设置成功");

            LambdaQueryWrapper<Departdetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Departdetail::getCartype, departUploadEntity.getCartype());
            wrapper.eq(Departdetail::getMonth, departUploadEntity.getMonth());
            wrapper.eq(Departdetail::getYear, departUploadEntity.getYear());
            //wrapper.eq(Departdetail::getSheet, departUploadEntity.getSheetname());

            List<Departdetail> departdetails = departDetailDao.selectList(wrapper);
            if (departdetails.size() >= 1) {
                responseEntity.setResMessage("【"+departUploadEntity.getYear()+"年" + RequestUtil.getObjectValue(requestParam, "cartype") +
                        RequestUtil.getObjectValue(requestParam, "type") + RequestUtil.getObjectValue(requestParam, "month") + "月份】  "
                        + "的数据已经有" + departdetails.size() + "条，再次新增会导致有多条数据，是否要先删除他们?");
                responseEntity.setRes(ConstantUtil.RESPONSE_WARNING);
            }


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            logger.error(e.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping("deleteDepart")
    @ResponseBody
    public ResponseEntity deleteDepart() {
        ResponseEntity responseEntity = new ResponseEntity();
        DepartUploadEntity departUploadEntity = ConstantUtil.departUploadEntity;
        LambdaQueryWrapper<Departdetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Departdetail::getCartype, departUploadEntity.getCartype());
        wrapper.eq(Departdetail::getMonth, departUploadEntity.getMonth());
        //wrapper.eq(Departdetail::getSheet, departUploadEntity.getSheetname());
        int delete = departDetailDao.delete(wrapper);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("本次共删除【" + departUploadEntity.getCartype() + departUploadEntity.getType() + departUploadEntity.getMonth() + "月份】" + delete + "条数据");
        return responseEntity;
    }

    @RequestMapping("deleteIncome")
    @ResponseBody
    public ResponseEntity deleteIncome() {
        ResponseEntity responseEntity = new ResponseEntity();
        IncomeUploadEntity incomeUploadEntity = ConstantUtil.incomeUploadEntity;
        LambdaQueryWrapper<Incomestatement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Incomestatement::getCartype, incomeUploadEntity.getCartype());
        wrapper.eq(Incomestatement::getCarid, incomeUploadEntity.getSheetname());
        //wrapper.eq(Departdetail::getSheet, departUploadEntity.getSheetname());
        int delete = incomeStatementDao.delete(wrapper);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("本次共删除【" + incomeUploadEntity.getCartype() + incomeUploadEntity.getSheetname() + incomeUploadEntity.getType() + "】" + delete + "条数据");
        return responseEntity;
    }

    @RequestMapping("setIncomeParamter")
    @ResponseBody
    public ResponseEntity setIncomeParamter(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        IncomeUploadEntity incomeUploadEntity = ConstantUtil.incomeUploadEntity;
        String requestParam = RequestUtil.getJsonObjectData(request);


        try {
            incomeUploadEntity.setType(RequestUtil.getObjectValue(requestParam, "type"));
            incomeUploadEntity.setCartype(RequestUtil.getObjectValue(requestParam, "cartype"));
            incomeUploadEntity.setSheetname(RequestUtil.getObjectValue(requestParam, "sheetname"));
            incomeUploadEntity.setYear(RequestUtil.getObjectValue(requestParam, "year"));
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("设置成功");

            LambdaQueryWrapper<Incomestatement> queryWrapper = new LambdaQueryWrapper<Incomestatement>();
            queryWrapper.eq(Incomestatement::getCarid, incomeUploadEntity.getSheetname());
            queryWrapper.eq(Incomestatement::getCartype, incomeUploadEntity.getCartype());
            queryWrapper.eq(Incomestatement::getYear, incomeUploadEntity.getYear());
            List<Incomestatement> incomestatementList = incomeStatementDao.selectList(queryWrapper);
            if (incomestatementList.size() >= 1) {

                responseEntity.setResMessage("【" +incomeUploadEntity.getYear()+"年"+ incomeUploadEntity.getCartype() +
                        RequestUtil.getObjectValue(requestParam, "type") + "车牌号为" + incomeUploadEntity.getSheetname() + "】的数据已经有"
                        + incomestatementList.size() + "条，再次新增会导致有多条数据，是否要先删除他们?");
                responseEntity.setRes(ConstantUtil.RESPONSE_WARNING);
            }

        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            logger.error(e.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping("queryByUploadEntity")
    @ResponseBody
    public ResponseEntity queryByUploadEntity(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        LambdaQueryWrapper<Departdetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Departdetail::getMonth, ConstantUtil.departUploadEntity.getMonth())
                .eq(Departdetail::getSheet, ConstantUtil.departUploadEntity.getSheetname())
                .eq(Departdetail::getCartype, ConstantUtil.departUploadEntity.getCartype())
                .eq(Departdetail::getYear, ConstantUtil.departUploadEntity.getYear())
                .eq(Departdetail::getExcelname, ConstantUtil.departUploadEntity.getExcelname()).orderByAsc(Departdetail::getSheetid);

        List<Departdetail> departdetails = departDetailDao.selectList(queryWrapper);
        responseEntity.setResList(departdetails);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }


    @RequestMapping("queryByUploadIncomeEntity")
    @ResponseBody
    public ResponseEntity queryByUploadIncomeEntity(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        LambdaQueryWrapper<Incomestatement> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Incomestatement::getCarid, ConstantUtil.incomeUploadEntity.getSheetname())
                .eq(Incomestatement::getCartype, ConstantUtil.incomeUploadEntity.getCartype())
                .eq(Incomestatement::getYear, ConstantUtil.incomeUploadEntity.getYear());

        List<Incomestatement> incomestatementList = incomeStatementDao.selectList(queryWrapper);
        responseEntity.setResList(incomestatementList);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
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
