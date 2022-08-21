package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface IncomeStatementDao extends BaseMapper<Incomestatement> {
    List<IncomeExportEntity> exportIncome(@Param("carType")String cartype);

    List<Incomestatement> queryMonthMoney(@Param("carType") String carType);

    Incomestatement queryMonthMoneyOther(@Param("carType")String cartype,@Param("year")String year, @Param("otherRowTitleList")ArrayList<String> otherRowTitleList);

    Incomestatement queryMonthMoneyOther1(@Param("carType")String cartype, @Param("otherRowTitleList")ArrayList<String> otherRowTitleList);


    List<SelectEntity> querycarid();

    List<SelectEntity> querycarTypeByIncome();

    List<SelectEntity> querycolumnsByIncome();

    List<IndexPageEntity> queryincomeIndex();

    List<IndexPageEntity> queryIncomeWarning();

    int update(Incomestatement incomestatement, UpdateWrapper<Incomestatement> updateWrapper, ResponseEntity responseEntity);

    List<Incomestatement> querySumByColumn(@Param("cartype")String cartype);

}
