package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomeStatementDao extends BaseMapper<Incomestatement> {
    List<IncomeExportEntity> exportIncome();

    List<Incomestatement> queryMonthMoney(@Param("carType") String carType);

}
