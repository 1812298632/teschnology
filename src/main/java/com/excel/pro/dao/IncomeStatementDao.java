package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;
import com.excel.pro.entity.SelectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface IncomeStatementDao extends BaseMapper<Incomestatement> {
    List<IncomeExportEntity> exportIncome(@Param("carType")String cartype);

    List<Incomestatement> queryMonthMoney(@Param("carType") String carType);

    Incomestatement queryMonthMoneyOther(@Param("carType")String cartype, @Param("otherRowTitleList")ArrayList<String> otherRowTitleList);

    List<SelectEntity> querycarid();

    List<SelectEntity> querycarTypeByIncome();

    List<SelectEntity> querycolumnsByIncome();
}
