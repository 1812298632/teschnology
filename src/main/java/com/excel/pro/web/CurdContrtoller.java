package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.FuleDao;
import com.excel.pro.dao.IncomeStatementDao;
import com.excel.pro.entity.*;
import com.excel.pro.service.DepartService;
import com.excel.pro.service.FuleService;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CurdContrtoller {
    Logger logger = LoggerFactory.getLogger(CurdContrtoller.class);

    @Resource
    private DepartService departService;

    @Resource
    private DepartDetailDao departDetailDao;

    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private IncomeStatementDao incomeStatementDao;

    @Resource
    private FuleService fuleService;

    @Resource
    private FuleDao fuleDao;

    @RequestMapping("updateIncome")
    @ResponseBody
    public ResponseEntity updateIncome(HttpServletRequest request) {

        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String year = RequestUtil.getObjectValue(requestParam, "year");
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String carid = RequestUtil.getObjectValue(requestParam, "carid");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
            String onemonth = RequestUtil.getObjectValue(requestParam, "onemonth");
            String twomonth = RequestUtil.getObjectValue(requestParam, "twomonth");
            String threemonth = RequestUtil.getObjectValue(requestParam, "threemonth");
            String fourmonth = RequestUtil.getObjectValue(requestParam, "fourmonth");
            String fivemonth = RequestUtil.getObjectValue(requestParam, "fivemonth");
            String sixmonth = RequestUtil.getObjectValue(requestParam, "sixmonth");
            String sevenmonth = RequestUtil.getObjectValue(requestParam, "sevenmonth");
            String eightmonth = RequestUtil.getObjectValue(requestParam, "eightmonth");
            String ninemonth = RequestUtil.getObjectValue(requestParam, "ninemonth");
            String tenmonth = RequestUtil.getObjectValue(requestParam, "tenmonth");
            String eleventmonth = RequestUtil.getObjectValue(requestParam, "eleventmonth");
            String twelvemonth = RequestUtil.getObjectValue(requestParam, "twelvemonth");
            LambdaQueryWrapper<Incomestatement> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(Incomestatement::getCartype, cartype);
            queryWrapper.eq(Incomestatement::getYear, year);
            queryWrapper.eq(Incomestatement::getCarid, carid);
            queryWrapper.eq(Incomestatement::getColumnname, columnname);
            Incomestatement incomestatement = incomeStatementDao.selectOne(queryWrapper);

            if (!incomestatement.getOnemonth().toString().equals(onemonth)) {
                incomestatement.setOnemonth(Double.parseDouble(onemonth));
            }

            if (!incomestatement.getTwomonth().toString().equals(twomonth)) {
                incomestatement.setTwomonth(Double.parseDouble(twomonth));
            }

            if (!incomestatement.getThreemonth().toString().equals(threemonth)) {
                incomestatement.setThreemonth(Double.parseDouble(threemonth));
            }

            if (!incomestatement.getFourmonth().toString().equals(fourmonth)) {
                incomestatement.setFourmonth(Double.parseDouble(fourmonth));
            }

            if (!incomestatement.getFivemonth().toString().equals(fivemonth)) {
                incomestatement.setFivemonth(Double.parseDouble(fivemonth));
            }

            if (!incomestatement.getSixmonth().toString().equals(sixmonth)) {
                incomestatement.setSixmonth(Double.parseDouble(sixmonth));
            }

            if (!incomestatement.getSevenmonth().toString().equals(sevenmonth)) {
                incomestatement.setSevenmonth(Double.parseDouble(sevenmonth));
            }

            if (!incomestatement.getEightmonth().toString().equals(eightmonth)) {
                incomestatement.setEightmonth(Double.parseDouble(eightmonth));
            }

            if (!incomestatement.getNinemonth().toString().equals(ninemonth)) {
                incomestatement.setNinemonth(Double.parseDouble(ninemonth));
            }

            if (!incomestatement.getTenmonth().toString().equals(tenmonth)) {
                incomestatement.setTenmonth(Double.parseDouble(tenmonth));
            }

            if (!incomestatement.getEleventmonth().toString().equals(eleventmonth)) {
                incomestatement.setEleventmonth(Double.parseDouble(eleventmonth));
            }

            if (!incomestatement.getTwelvemonth().toString().equals(twelvemonth)) {
                incomestatement.setTwelvemonth(Double.parseDouble(twelvemonth));
            }

            UpdateWrapper<Incomestatement> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(Incomestatement::getCartype, cartype);
            updateWrapper.lambda().eq(Incomestatement::getCarid, carid);
            updateWrapper.lambda().eq(Incomestatement::getColumnname, columnname);


            incomeStatementService.update(incomestatement, updateWrapper, responseEntity);


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }

    @RequestMapping("delIncome")
    @ResponseBody
    public ResponseEntity delIncome(HttpServletRequest request) {

        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String carid = RequestUtil.getObjectValue(requestParam, "carid");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");

            LambdaQueryWrapper<Incomestatement> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(Incomestatement::getCartype, cartype);
            queryWrapper.eq(Incomestatement::getCarid, carid);
            queryWrapper.eq(Incomestatement::getColumnname, columnname);


            incomeStatementService.delete(queryWrapper, responseEntity);


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }


    @RequestMapping("updateDepart")
    @ResponseBody
    public ResponseEntity updateDepart(HttpServletRequest request) {

        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String sheetid = RequestUtil.getObjectValue(requestParam, "sheetid");
            String month = RequestUtil.getObjectValue(requestParam, "month");
            String fromno = RequestUtil.getObjectValue(requestParam, "fromno");
            String carnum = RequestUtil.getObjectValue(requestParam, "carnum");
            String startcity = RequestUtil.getObjectValue(requestParam, "startcity");
            String endcity = RequestUtil.getObjectValue(requestParam, "endcity");
            String startkilo = RequestUtil.getObjectValue(requestParam, "startkilo");
            String endkilo = RequestUtil.getObjectValue(requestParam, "endkilo");
            String kilo = RequestUtil.getObjectValue(requestParam, "kilo");
            String ismonthcount = RequestUtil.getObjectValue(requestParam, "ismonthcount");
            String iskilosum = RequestUtil.getObjectValue(requestParam, "iskilosum");

            LambdaQueryWrapper<Departdetail> queryWrapper = new LambdaQueryWrapper();

            queryWrapper.eq(Departdetail::getCartype, cartype);
            queryWrapper.eq(Departdetail::getSheetid, sheetid);
            queryWrapper.eq(Departdetail::getMonth, month);
            Departdetail departdetail = departDetailDao.selectOne(queryWrapper);

            if (!startcity.equals(departdetail.getStartcity())) {
                departdetail.setStartcity(startcity);
            }
            if (!endcity.equals(departdetail.getEndcity())) {
                departdetail.setEndcity(endcity);
            }
            if (!month.equals(departdetail.getMonth())) {
                departdetail.setMonth(Long.parseLong(month));
            }


            if (!startkilo.equals(departdetail.getStartkilo())) {
                departdetail.setStartkilo(Long.parseLong(startkilo));
            }

            if (!endkilo.equals(departdetail.getEndkilo())) {
                departdetail.setEndkilo(Long.parseLong(endkilo));
            }
            if (!kilo.equals(departdetail.getKilo())) {
                departdetail.setKilo(Long.parseLong(kilo));
            }

            if (!ismonthcount.equals(departdetail.getIsmonthcount())) {
                departdetail.setIsmonthcount(ismonthcount);
            }

            if (!iskilosum.equals(departdetail.getIskilosum())) {
                departdetail.setIskilosum(iskilosum);
            }


            UpdateWrapper<Departdetail> updateWrapper = new UpdateWrapper<>();

            updateWrapper.lambda().eq(Departdetail::getCartype, cartype);
            updateWrapper.lambda().eq(Departdetail::getSheetid, sheetid);
            updateWrapper.lambda().eq(Departdetail::getMonth, month);


            departService.update(departdetail, updateWrapper, responseEntity);


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }


    @RequestMapping("delDepart")
    @ResponseBody
    public ResponseEntity delDepart(HttpServletRequest request) {

        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String sheetid = RequestUtil.getObjectValue(requestParam, "sheetid");
            String month = RequestUtil.getObjectValue(requestParam, "month");

            LambdaQueryWrapper<Departdetail> queryWrapper = new LambdaQueryWrapper();

            queryWrapper.eq(Departdetail::getCartype, cartype);
            queryWrapper.eq(Departdetail::getSheetid, sheetid);
            queryWrapper.eq(Departdetail::getMonth, month);
            Departdetail departdetail = departDetailDao.selectOne(queryWrapper);


            departService.delete(queryWrapper, responseEntity);


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }

    @RequestMapping("insertFule")
    @ResponseBody
    public ResponseEntity insertFule(HttpServletRequest request) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String year = RequestUtil.getObjectValue(requestParam, "year");
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
            String onemonth = RequestUtil.getObjectValue(requestParam, "onemonth");
            String twomonth = RequestUtil.getObjectValue(requestParam, "twomonth");
            String threemonth = RequestUtil.getObjectValue(requestParam, "threemonth");
            String fourmonth = RequestUtil.getObjectValue(requestParam, "fourmonth");
            String fivemonth = RequestUtil.getObjectValue(requestParam, "fivemonth");
            String sixmonth = RequestUtil.getObjectValue(requestParam, "sixmonth");
            String sevenmonth = RequestUtil.getObjectValue(requestParam, "sevenmonth");
            String eightmonth = RequestUtil.getObjectValue(requestParam, "eightmonth");
            String ninemonth = RequestUtil.getObjectValue(requestParam, "ninemonth");
            String tenmonth = RequestUtil.getObjectValue(requestParam, "tenmonth");
            String eleventmonth = RequestUtil.getObjectValue(requestParam, "eleventmonth");
            String twelvemonth = RequestUtil.getObjectValue(requestParam, "twelvemonth");
            String kiloSum = RequestUtil.getObjectValue(requestParam, "kilosum");

            Fule fule = new Fule();
            fule.setYear(Long.parseLong(year));
            fule.setCartype(cartype);
            fule.setColumnname(columnname);
            if (!StringUtils.isEmpty(onemonth)) {
                fule.setOnemonth(Double.parseDouble(onemonth));
            }
            if (!StringUtils.isEmpty(twomonth)) {
                fule.setTwomonth(Double.parseDouble(twomonth));

            }
            if (!StringUtils.isEmpty(threemonth)) {
                fule.setThreemonth(Double.parseDouble(threemonth));

            }
            if (!StringUtils.isEmpty(fourmonth)) {
                fule.setFourmonth(Double.parseDouble(fourmonth));

            }
            if (!StringUtils.isEmpty(fivemonth)) {
                fule.setFivemonth(Double.parseDouble(fivemonth));

            }
            if (!StringUtils.isEmpty(sixmonth)) {
                fule.setSixmonth(Double.parseDouble(sixmonth));

            }
            if (!StringUtils.isEmpty(sevenmonth)) {
                fule.setSevenmonth(Double.parseDouble(sevenmonth));

            }
            if (!StringUtils.isEmpty(eightmonth)) {
                fule.setEightmonth(Double.parseDouble(eightmonth));

            }
            if (!StringUtils.isEmpty(ninemonth)) {
                fule.setNinemonth(Double.parseDouble(ninemonth));

            }
            if (!StringUtils.isEmpty(tenmonth)) {
                fule.setTenmonth(Double.parseDouble(tenmonth));

            }
            if (!StringUtils.isEmpty(eleventmonth)) {
                fule.setEleventmonth(Double.parseDouble(eleventmonth));

            }
            if (!StringUtils.isEmpty(twelvemonth)) {
                fule.setTwelvemonth(Double.parseDouble(twelvemonth));

            }

            if (!StringUtils.isEmpty(kiloSum)) {
                fule.setKilosum(Double.parseDouble(kiloSum));
            }

            fuleService.insert(fule, responseEntity);

        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }


    @RequestMapping("updateFule")
    @ResponseBody
    public ResponseEntity updateFule(HttpServletRequest request) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String year = RequestUtil.getObjectValue(requestParam, "year");
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
            String onemonth = RequestUtil.getObjectValue(requestParam, "onemonth");
            String twomonth = RequestUtil.getObjectValue(requestParam, "twomonth");
            String threemonth = RequestUtil.getObjectValue(requestParam, "threemonth");
            String fourmonth = RequestUtil.getObjectValue(requestParam, "fourmonth");
            String fivemonth = RequestUtil.getObjectValue(requestParam, "fivemonth");
            String sixmonth = RequestUtil.getObjectValue(requestParam, "sixmonth");
            String sevenmonth = RequestUtil.getObjectValue(requestParam, "sevenmonth");
            String eightmonth = RequestUtil.getObjectValue(requestParam, "eightmonth");
            String ninemonth = RequestUtil.getObjectValue(requestParam, "ninemonth");
            String tenmonth = RequestUtil.getObjectValue(requestParam, "tenmonth");
            String eleventmonth = RequestUtil.getObjectValue(requestParam, "eleventmonth");
            String twelvemonth = RequestUtil.getObjectValue(requestParam, "twelvemonth");
            String kiloSum = RequestUtil.getObjectValue(requestParam, "kilosum");

            LambdaQueryWrapper<Fule> fuleWrapper = new LambdaQueryWrapper();

            fuleWrapper.eq(Fule::getCartype, cartype);
            fuleWrapper.eq(Fule::getYear, year);
            fuleWrapper.eq(Fule::getColumnname, columnname);

            Fule fule = fuleDao.selectOne(fuleWrapper);


            if (StringUtils.isEmpty(onemonth) || onemonth.equals("null")) {
                fule.setOnemonth(null);
            } else {
                fule.setOnemonth(Double.parseDouble(onemonth));
            }

            if (StringUtils.isEmpty(twomonth) || twomonth.equals("null")) {
                fule.setTwomonth(null);
            } else {
                fule.setTwomonth(Double.parseDouble(twomonth));
            }
            if (StringUtils.isEmpty(threemonth) || threemonth.equals("null")) {
                fule.setThreemonth(null);
            } else {
                fule.setThreemonth(Double.parseDouble(threemonth));
            }

            if (StringUtils.isEmpty(fourmonth) || fourmonth.equals("null")) {
                fule.setFourmonth(null);
            } else {
                fule.setFourmonth(Double.parseDouble(fourmonth));
            }

            if (StringUtils.isEmpty(fivemonth) || fivemonth.equals("null")) {
                fule.setFivemonth(null);
            } else {
                fule.setFivemonth(Double.parseDouble(fivemonth));
            }

            if (StringUtils.isEmpty(sixmonth) || sixmonth.equals("null")) {
                fule.setSixmonth(null);
            } else {
                fule.setSixmonth(Double.parseDouble(sixmonth));
            }

            if (StringUtils.isEmpty(sevenmonth) || sevenmonth.equals("null")) {
                fule.setSevenmonth(null);
            } else {
                fule.setSevenmonth(Double.parseDouble(sevenmonth));
            }

            if (StringUtils.isEmpty(eightmonth) || eightmonth.equals("null")) {
                fule.setEightmonth(null);
            } else {
                fule.setEightmonth(Double.parseDouble(eightmonth));
            }

            if (StringUtils.isEmpty(ninemonth) || ninemonth.equals("null")) {
                fule.setNinemonth(null);
            } else {
                fule.setNinemonth(Double.parseDouble(ninemonth));
            }

            if (StringUtils.isEmpty(tenmonth) || tenmonth.equals("null")) {
                fule.setTenmonth(null);
            } else {
                fule.setTenmonth(Double.parseDouble(tenmonth));
            }

            if (StringUtils.isEmpty(eleventmonth) || eleventmonth.equals("null")) {
                fule.setEleventmonth(null);
            } else {
                fule.setEleventmonth(Double.parseDouble(eleventmonth));
            }
            if (StringUtils.isEmpty(twelvemonth) || twelvemonth.equals("null")) {
                fule.setTwelvemonth(null);
            } else {
                fule.setTwelvemonth(Double.parseDouble(twelvemonth));
            }

            if (StringUtils.isEmpty(kiloSum) || kiloSum.equals("null")) {
                fule.setKilosum(null);
            } else {
                fule.setKilosum(Double.parseDouble(kiloSum));
            }

            fuleService.update(fule, fuleWrapper, responseEntity);
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }


    @RequestMapping("deleteFule")
    @ResponseBody
    public ResponseEntity deleteFule(HttpServletRequest request) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity();


            String requestParam = RequestUtil.getJsonObjectData(request);
            String year = RequestUtil.getObjectValue(requestParam, "year");
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");


            LambdaQueryWrapper<Fule> fuleWrapper = new LambdaQueryWrapper();

            fuleWrapper.eq(Fule::getCartype, cartype);
            fuleWrapper.eq(Fule::getYear, year);
            fuleWrapper.eq(Fule::getColumnname, columnname);


            fuleService.delete(fuleWrapper, responseEntity);


        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }




}
