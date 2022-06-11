package com.excel.pro.service;

import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface IncomeStatementService {
    void insertIncomeStatement(String sheetName) throws FileNotFoundException;

    List<IncomeExportEntity> exportIncome();

    List<Incomestatement> queryMonthMoney(String catType);

    Incomestatement queryMonthMoneyOther(String cartype, ArrayList<String> otherRowTitleList);

    void insertIncomeStatementByUpload(XSSFSheet sheet);
}
