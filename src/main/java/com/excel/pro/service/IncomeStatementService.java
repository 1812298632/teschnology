package com.excel.pro.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;
import com.excel.pro.entity.ResponseEntity;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface IncomeStatementService {
    void insertIncomeStatement(String sheetName) throws FileNotFoundException;

    List<IncomeExportEntity> exportIncome(String cartype,String year);

    List<Incomestatement> queryMonthMoney(String catType,String year);

    Incomestatement queryMonthMoneyOther(String cartype,String year, ArrayList<String> otherRowTitleList);

    void insertIncomeStatementByUpload(XSSFSheet sheet);

    void update(Incomestatement incomestatement, UpdateWrapper<Incomestatement> updateWrapper, ResponseEntity responseEntity);

    void delete( LambdaQueryWrapper<Incomestatement> queryWrapper, ResponseEntity responseEntity);

    List<Incomestatement> querySumByColumn(String cartype,String year );

    List<IncomeExportEntity> exportIncomeAll(String cartype, String year);
}
