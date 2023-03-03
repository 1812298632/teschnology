package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.Fule;
import com.excel.pro.entity.Incomestatement;
import com.excel.pro.entity.SelectEntity;

import java.util.List;

public interface FuleDao extends BaseMapper<Fule> {
    List<SelectEntity> queryFuleType();

    List<SelectEntity> queryFuleYear();

    List<SelectEntity> quertFuleColumns();

    Incomestatement queryMonthMoney(String year, String cartype, String columnname);

    List<Incomestatement> queryFuleSum(String cartype, String year);

    String getkiloSum(String cartype, String year);
}
