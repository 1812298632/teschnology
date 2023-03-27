package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.FuleDao;
import com.excel.pro.dao.IncomeStatementDao;
import com.excel.pro.entity.*;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import com.sun.deploy.net.HttpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QueryController {
    @Resource
    private DepartDetailDao departDetailDao;

    @Resource
    private IncomeStatementDao incomeStatementDao;

    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private FuleDao fuleDao;

    /**
     * 台账数据查询中，起点下拉框
     * @return
     */
    @RequestMapping("/queryCitySelect")
    public ResponseEntity queryCitySelect() {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryCitySelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    /**
     * 台账数据查询中，终点下拉框
     * @return
     */
    @RequestMapping("/queryEndCitySelect")
    public ResponseEntity queryEndCitySelect() {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryEndCitySelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }

    /**
     * 台账数据查询中，月份下拉框
     * @param request
     * @return
     */
    @RequestMapping("/queryMonthSelect")
    public ResponseEntity queryMonthSelect(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryMonthSelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }

    /**
     * 台账数据查询中，年份下拉框
     * @param request
     * @return
     */
    @RequestMapping("/queryYearSelect")
    public ResponseEntity queryYearSelect(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryYearSelect();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }

    /**
     * 台账数据查询中，车辆类型下拉框
     * @param request
     * @return
     */
    @RequestMapping("/queryCarTypeByDepart")
    public ResponseEntity queryCarTypeByDepart(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = departDetailDao.queryCarTypeByDepart();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    /**
     * 损益表查询中，车牌号下拉框
     * @param request
     * @return
     */
    @RequestMapping("/querycarid")
    public ResponseEntity querycarid(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycarid();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    /**
     * 损益表查询中，车辆类型下拉框
     * @param request
     * @return
     */
    @RequestMapping("/querycarTypeByIncome")
    public ResponseEntity querycarTypeByIncome(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycarTypeByIncome();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    /**
     * 损益表查询中，项目下拉框
     * @param request
     * @return
     */
    @RequestMapping("/querycolumnsByIncome")
    public ResponseEntity querycolumnsByIncome(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = incomeStatementDao.querycolumnsByIncome();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }



    @RequestMapping("/queryFuleType")
    public ResponseEntity queryFuleType(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = fuleDao.queryFuleType();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }
    @RequestMapping("/queryFuleYear")
    public ResponseEntity queryFuleYear(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = fuleDao.queryFuleYear();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }

    @RequestMapping("/quertFuleColumns")
    public ResponseEntity quertFuleColumns(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<SelectEntity> selectEntityList = fuleDao.quertFuleColumns();

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }

    /**
     * 查询台账列表，如果有查询条件，将查询条件拼接
     * @param request
     * @return
     */
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
            String year = RequestUtil.getObjectValue(requestParam, "year");
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
            if(!year.equals("")){
                queryWrapper.lambda().eq(Departdetail::getYear,year);
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


    /**
     * 查询损益表列表，如果有查询条件，将查询条件拼接
     * @param request
     * @return
     */
    @RequestMapping("/queryIncomeList")
    public ResponseEntity queryIncomeList(HttpServletRequest request) {
        QueryWrapper<Incomestatement> queryWrapper = new QueryWrapper<>();

        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        if(!requestParam.equals("")){
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String carid = RequestUtil.getObjectValue(requestParam, "carid");
            String year = RequestUtil.getObjectValue(requestParam, "year");
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
            if(!year.equals("")){
                queryWrapper.lambda().eq(Incomestatement::getYear,year);
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

    @RequestMapping("/queryRunCount")
    public ResponseEntity queryRunCount(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");
        List<SelectEntity> selectEntityList = departDetailDao.queryRunCount(cartype,year);

        responseEntity.setResList(selectEntityList);
        return responseEntity;
    }


    /**
     * 查询损益表中所有数据，根据cartype区分  沃尔沃/解放车
     * @param request
     * @return
     */
    @RequestMapping("/queryIncomeMoney")
    public ResponseEntity queryjfmoney(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");
        List<Incomestatement> incomestatements = departDetailDao.queryIncomeMoney(cartype,year);


        responseEntity.setResList(incomestatements);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询损益表中其他列的数据
     * @param request
     * @return
     */
    @RequestMapping("/queryOthermoney")
    public ResponseEntity queryOthermoney(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");
        List<Incomestatement> incomestatements = new ArrayList<>();


        //毛利表中 其他列
        ArrayList<String> otherRowTitleList = new ArrayList<>();
        otherRowTitleList.add(ConstantUtil.title13);
        otherRowTitleList.add(ConstantUtil.title11);

        Incomestatement otherMoney = incomeStatementDao.queryMonthMoneyOther(cartype,year, otherRowTitleList);


        if(otherMoney != null){
            incomestatements.add(otherMoney);

        }

        responseEntity.setResList(incomestatements);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询台账表已经导入的月份数据，只查询月份
     * @param request
     * @return
     */
    @RequestMapping("/queryDepartIndex")
    public ResponseEntity queryDepartIndex(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();


        List<IndexPageEntity> indexPageEntities = departDetailDao.selectCountGroupByMonth();



        responseEntity.setResList(indexPageEntities);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }

    /**
     * 查询台账表 每个月的发车次数
     * @param request
     * @return
     */
    @RequestMapping("/queryDepartIndex1")
    public ResponseEntity queryDepartIndex1(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<IndexPageEntity> indexPageEntities = departDetailDao.selectCountClassify();

        responseEntity.setResList(indexPageEntities);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询台账表可能会有异常的数据，并显示在首页
     * @param request
     * @return
     */
    @RequestMapping("/queryDepartWarning")
    public ResponseEntity queryDepartWarring(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<IndexPageEntity> indexPageEntities = departDetailDao.queryDepartWarring();

        responseEntity.setResList(indexPageEntities);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询损溢表在首页的数据统计
     * @param request
     * @return
     */
    @RequestMapping("/queryIncomeIndex")
    public ResponseEntity queryIncomeIndex(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<IndexPageEntity> indexPageEntities = incomeStatementDao.queryincomeIndex();

        responseEntity.setResList(indexPageEntities);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询损益表可能会有异常的数据，并显示在首页
     * @param request
     * @return
     */
    @RequestMapping("/queryIncomeWarning")
    public ResponseEntity queryIncomeWarning(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();

        List<IndexPageEntity> indexPageEntities = incomeStatementDao.queryIncomeWarning();

        responseEntity.setResList(indexPageEntities);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }


    /**
     * 查询百公里油耗相关数据
     * @param request
     * @return
     */
    @RequestMapping("/queryFuleList")
    public ResponseEntity queryFuleList(HttpServletRequest request) {
        QueryWrapper<Fule> queryWrapper = new QueryWrapper<>();

        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        if(!requestParam.equals("")){
            String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
            String year = RequestUtil.getObjectValue(requestParam, "year");
            String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
            if(!cartype.equals("")){
                queryWrapper.lambda().eq(Fule::getCartype,cartype);
            }
            if(!year.equals("")){
                queryWrapper.lambda().eq(Fule::getYear,year);
            }
            if(!columnname.equals("")){
                queryWrapper.lambda().eq(Fule::getColumnname,columnname);
            }

        }

        queryWrapper.lambda().orderByAsc(Fule::getYear);
        //queryWrapper.lambda().orderByAsc(Fule::getCarid);
        //queryWrapper.lambda().orderByAsc(Fule::getColumnname);

        List<Fule> fuleList = fuleDao.selectList(queryWrapper);

        responseEntity.setResList(fuleList);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }


    @RequestMapping("/queryFuleMoney")
    public ResponseEntity queryFuleMoney(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");
        List<Fule> fuleList = new ArrayList<>();

        LambdaQueryWrapper<Fule> fuleWrapper = new LambdaQueryWrapper<>();

        fuleWrapper.eq(Fule::getYear,year);
        fuleWrapper.eq(Fule::getCartype,cartype);
        fuleWrapper.eq(Fule::getColumnname,"加油升数");

        Fule fule = fuleDao.selectOne(fuleWrapper);
        if(fule != null){
            fuleList.add(fule);

        }
        responseEntity.setResList(fuleList);
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        return responseEntity;
    }
}
