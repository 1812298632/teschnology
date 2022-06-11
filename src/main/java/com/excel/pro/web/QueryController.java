package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.IncomeStatementDao;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.Incomestatement;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.entity.SelectEntity;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class QueryController {
    @Resource
    DepartDetailDao departDetailDao;

    @Resource
    IncomeStatementDao incomeStatementDao;

    @RequestMapping("/queryCitySelect")
    public ResponseEntity queryCitySelect() {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryCitySelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/queryEndCitySelect")
    public ResponseEntity queryEndCitySelect() {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryEndCitySelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/queryJFMonthSelect")
    public ResponseEntity queryJFMonthSelect(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryJFMonthSelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/queryCarTypeByDepart")
    public ResponseEntity queryCarTypeByDepart(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryCarTypeByDepart();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/querycarid")
    public ResponseEntity querycarid(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycarid();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/querycarTypeByIncome")
    public ResponseEntity querycarTypeByIncome(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycarTypeByIncome();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    @RequestMapping("/querycolumnsByIncome")
    public ResponseEntity querycolumnsByIncome(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycolumnsByIncome();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }



    @RequestMapping("/queryDeaprtList")
    public ResponseEntity queryDeaprtList(HttpServletRequest request) {
        QueryWrapper<Departdetail> queryWrapper = new QueryWrapper<>();

        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        if(!requestParam.equals("")){
            String type = RequestUtil.getObjectValue(requestParam, "type");
            String startcity = RequestUtil.getObjectValue(requestParam, "startcity");
            String endcity = RequestUtil.getObjectValue(requestParam, "endcity");
            String month = RequestUtil.getObjectValue(requestParam, "month");
            if(!type.equals("")){
                queryWrapper.lambda().eq(Departdetail::getCartype,type);
            }
            if(!startcity.equals("")){
                queryWrapper.lambda().eq(Departdetail::getStartcity,startcity);
            }
            if(!endcity.equals("")){
                queryWrapper.lambda().eq(Departdetail::getEndcity,endcity);
            }
            if(!month.equals("")){
                queryWrapper.lambda().eq(Departdetail::getMonth,month);
            }
        }

        queryWrapper.lambda().orderByAsc(Departdetail::getMonth);
        queryWrapper.lambda().orderByAsc(Departdetail::getSheetid);
        queryWrapper.lambda().orderByAsc(Departdetail::getCartype);
        List<Departdetail> departdetails = departDetailDao.selectList(queryWrapper);

        responseEntity.setResList(departdetails);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }



    @RequestMapping("/queryIncomeList")
    public ResponseEntity queryIncomeList(HttpServletRequest request) {
        QueryWrapper<Incomestatement> queryWrapper = new QueryWrapper<>();

        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        if(!requestParam.equals("")){
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String carid = RequestUtil.getObjectValue(requestParam, "carid");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
            if(!cartype.equals("")){
                queryWrapper.lambda().eq(Incomestatement::getCartype,cartype);
            }
            if(!carid.equals("")){
                queryWrapper.lambda().eq(Incomestatement::getCarid,carid);
            }
            if(!columnname.equals("")){
                queryWrapper.lambda().eq(Incomestatement::getColumnname,columnname);
            }

        }

        queryWrapper.lambda().orderByAsc(Incomestatement::getCartype);
        queryWrapper.lambda().orderByAsc(Incomestatement::getCarid);
        queryWrapper.lambda().orderByAsc(Incomestatement::getColumnname);
        List<Incomestatement> incomestatementList = incomeStatementDao.selectList(queryWrapper);

        responseEntity.setResList(incomestatementList);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }

}
