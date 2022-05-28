package com.excel.pro.service;

import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;

import java.io.FileNotFoundException;
import java.util.List;

public interface IncomeStatementService {
    void insertIncomeStatement(String sheetName) throws FileNotFoundException;

    List<IncomeExportEntity> exportIncome();

    List<Incomestatement> queryMonthMoney(String catType);

}
