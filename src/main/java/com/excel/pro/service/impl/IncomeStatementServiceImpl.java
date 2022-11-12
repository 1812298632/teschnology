package com.excel.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.dao.IncomeStatementDao;
import com.excel.pro.entity.IncomeExportEntity;
import com.excel.pro.entity.Incomestatement;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.util.ConstantUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class IncomeStatementServiceImpl implements IncomeStatementService {
    Logger logger = LoggerFactory.getLogger(IncomeStatementServiceImpl.class);

    @Resource
    private IncomeStatementDao incomeStatementDao;

    @Override
    public void insertIncomeStatement(String sheetName) throws FileNotFoundException {
        File file = new File("G:\\msg\\qq\\1812298632\\FileRecv\\2021年2月损益表-中卡车队.XLSX");

        FileInputStream in = null;
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        // 创建对Excel工作簿文件的引用
        in = new FileInputStream(file);
        XSSFWorkbook excel = null;
        try {
            excel = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = excel.getSheet(sheetName);


        int titleRowIndex = -1;
        String titleRowAndColumnName = "项  目";

       /* ArrayList<String> columnNameList = new ArrayList<>();
        columnNameList.add("科目编码");
        columnNameList.add("1月");
        columnNameList.add("2月");
        columnNameList.add("3月");
        columnNameList.add("4月");
        columnNameList.add("5月");
        columnNameList.add("6月");
        columnNameList.add("7月");
        columnNameList.add("8月");
        columnNameList.add("9月");
        columnNameList.add("10月");
        columnNameList.add("11月");
        columnNameList.add("12月");*/


        //需要保存哪些列的数据
        ArrayList<String> columnNameList = ConstantUtil.makeIncomeImportTitle();


        //获取需要插入表中的数据列的下标
        for (Row row : sheet) {
            //for循环row中的所有sheet
            for (Cell cell : row) {
                for (String s : columnNameList) {
                    if (formatter.formatCellValue(cell).equals(s)) {//将获取到的cell数据格式化，不然会出错
                        //如果进入了if判断，说明找到了数据，将列的下标存入map中
                        titleColumnMap.put(s, cell.getColumnIndex());
                    }
                }
                if (formatter.formatCellValue(cell).equals(titleRowAndColumnName)) {
                    titleRowIndex = cell.getColumnIndex();
                }
            }
        }

       /* ArrayList<String> rowNameList = new ArrayList<>();
        //需要保存哪些行title的数据
        rowNameList.add("   车队收入");
        rowNameList.add("       车队营业收人");
        rowNameList.add("         燃油费");
        rowNameList.add("         路桥费");
        rowNameList.add("         停车费");
        rowNameList.add("         罚金、滞纳金");
        rowNameList.add("         车辆维修费");
        rowNameList.add("            日常维修");
        rowNameList.add("            轮胎维修");
        rowNameList.add("            大修基金");
        rowNameList.add("       差旅费");
        rowNameList.add("       车队其他");*/
        ArrayList<String> rowNameList = ConstantUtil.makeIncomeImportRowTitle();


        for (Row row : sheet) {
            //获取从第五行开始的数据，第五行之前都是标题
            if (row.getRowNum() >= 4) {
                for (String rowName : rowNameList) {
                    if (formatter.formatCellValue(row.getCell(titleRowIndex)).equals(rowName)) {
                        Incomestatement incomestatement = new Incomestatement();
                        incomestatement.setCarid(sheetName);

                        if (sheetName.equals("解放车") || sheetName.equals("苏BX6615") || sheetName.equals("苏BX6667") || sheetName.equals("苏BX6609")) {
                            incomestatement.setCartype("解放车");
                        } else {
                            incomestatement.setCartype("沃尔沃");
                        }
                        incomestatement.setColumnname(rowName);
                        String subjectCode = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));
                        incomestatement.setSubjectcode(subjectCode);

                        double mon1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())).getNumericCellValue();
                        double mon2 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())).getNumericCellValue();
                        double mon3 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())).getNumericCellValue();
                        double mon4 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString())).getNumericCellValue();
                        double mon5 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString())).getNumericCellValue();
                        double mon6 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString())).getNumericCellValue();
                        double mon7 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())).getNumericCellValue();
                        double mon8 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(8)).toString())).getNumericCellValue();
                        double mon9 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(9)).toString())).getNumericCellValue();
                        double mon10 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(10)).toString())).getNumericCellValue();
                        double mon11 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(11)).toString())).getNumericCellValue();
                        double mon12 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(12)).toString())).getNumericCellValue();


                        incomestatement.setOnemonth(new BigDecimal(mon1).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setTwomonth(new BigDecimal(mon2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setThreemonth(new BigDecimal(mon3).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setFourmonth(new BigDecimal(mon4).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setFivemonth(new BigDecimal(mon5).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setSixmonth(new BigDecimal(mon6).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setSevenmonth(new BigDecimal(mon7).setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setEightmonth(new BigDecimal(mon8).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setNinemonth(new BigDecimal(mon9).setScale(9, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setTenmonth(new BigDecimal(mon10).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setEleventmonth(new BigDecimal(mon11).setScale(11, BigDecimal.ROUND_HALF_UP).doubleValue());
                        incomestatement.setTwelvemonth(new BigDecimal(mon12).setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue());

                        //获取一月份的数据
                       /* if (!StringUtils.isEmpty(mon1)) {
                            //incomestatement.setOnemonth(Double.parseDouble(mon1));
                            incomestatement.setOnemonth(mon1);
                        }
                        if (!StringUtils.isEmpty(mon2)) {
                            incomestatement.setTwomonth(Double.parseDouble(mon2));
                        }
                        if (!StringUtils.isEmpty(mon3)) {
                            incomestatement.setThreemonth(Double.parseDouble(mon3));
                        }
                        if (!StringUtils.isEmpty(mon4)) {
                            incomestatement.setFourmonth(Double.parseDouble(mon4));
                        }
                        if (!StringUtils.isEmpty(mon5)) {
                            incomestatement.setFivemonth(Double.parseDouble(mon5));
                        }
                        if (!StringUtils.isEmpty(mon6)) {
                            incomestatement.setSixmonth(Double.parseDouble(mon6));
                        }
                        if (!StringUtils.isEmpty(mon7)) {
                            incomestatement.setSevenmonth(Double.parseDouble(mon7));
                        }
                        if (!StringUtils.isEmpty(mon8)) {
                            incomestatement.setEightmonth(Double.parseDouble(mon8));
                        }
                        if (!StringUtils.isEmpty(mon9)) {
                            incomestatement.setNinemonth(Double.parseDouble(mon9));
                        }
                        if (!StringUtils.isEmpty(mon10)) {
                            incomestatement.setTenmonth(Double.parseDouble(mon10));
                        }
                        if (!StringUtils.isEmpty(mon11)) {
                            incomestatement.setEleventmonth(Double.parseDouble(mon11));
                        }
                        if (!StringUtils.isEmpty(mon12)) {
                            incomestatement.setTwelvemonth(Double.parseDouble(mon12));
                        }*/
                        logger.info("开始插入车牌号" + sheetName + "title为" + rowName + "的数据");
                        incomeStatementDao.insert(incomestatement);
                        logger.info("车牌号" + sheetName + "title为" + rowName + "的数据插入成功");

                    }
                }

            }
        }


    }

    @Override
    public List<IncomeExportEntity> exportIncome(String cartype,String year) {
        return incomeStatementDao.exportIncome(cartype,year);
    }

    @Override
    public List<Incomestatement> queryMonthMoney(String carType,String year) {
        return incomeStatementDao.queryMonthMoney(carType,year);
    }

    @Override
    public Incomestatement queryMonthMoneyOther(String cartype, String year,ArrayList<String> otherRowTitleList) {
        return incomeStatementDao.queryMonthMoneyOther1(cartype, year,otherRowTitleList);
    }

    @Override
    public void insertIncomeStatementByUpload(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();

        int titleRowIndex = -1;
        String titleRowAndColumnName = "项  目";
        //需要保存哪些列的数据
        ArrayList<String> columnNameList = ConstantUtil.makeIncomeImportTitle();


        //获取需要插入表中的数据列的下标
        for (Row row : sheet) {
            //for循环row中的所有sheet
            for (Cell cell : row) {
                for (String s : columnNameList) {
                    if (formatter.formatCellValue(cell).equals(s)) {//将获取到的cell数据格式化，不然会出错
                        //如果进入了if判断，说明找到了数据，将列的下标存入map中
                        titleColumnMap.put(s, cell.getColumnIndex());
                    }
                }
                if (formatter.formatCellValue(cell).equals(titleRowAndColumnName)) {
                    titleRowIndex = cell.getColumnIndex();
                }
            }
        }

        ArrayList<String> rowNameList = new ArrayList<>();

        //表头可能会发生改变 根据年份来选择不同表头
        if (ConstantUtil.incomeUploadEntity.getYear().equals("2022")) {
            rowNameList = ConstantUtil.makeIncomeImportRowTitle2022();
        } else if (ConstantUtil.incomeUploadEntity.getYear().equals("2021")) {
            rowNameList = ConstantUtil.makeIncomeImportRowTitle();
        } else {
            rowNameList = ConstantUtil.makeIncomeImportRowTitle2022();
        }


        String sheetName = ConstantUtil.incomeUploadEntity.getSheetname();

        for (Row row : sheet) {
            //获取从第五行开始的数据，第五行之前都是标题
            if (row.getRowNum() >= 4) {
                for (String rowName : rowNameList) {
                    if (formatter.formatCellValue(row.getCell(titleRowIndex)).equals(rowName)) {
                        Incomestatement incomestatement = new Incomestatement();
                        incomestatement.setCarid(sheetName);


                        incomestatement.setCartype(ConstantUtil.incomeUploadEntity.getCartype());

                        incomestatement.setColumnname(rowName);
                        String subjectCode = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));
                        incomestatement.setSubjectcode(subjectCode);

                        int i = Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString());
                        double mon1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())).getNumericCellValue();
                        double mon2 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())).getNumericCellValue();
                        double mon3 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())).getNumericCellValue();
                        double mon4 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString())).getNumericCellValue();
                        double mon5 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString())).getNumericCellValue();
                        double mon6 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString())).getNumericCellValue();
                        double mon7 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())).getNumericCellValue();
                        double mon8 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(8)).toString())).getNumericCellValue();
                        double mon9 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(9)).toString())).getNumericCellValue();
                        double mon10 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(10)).toString())).getNumericCellValue();
                        double mon11 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(11)).toString())).getNumericCellValue();
                        double mon12 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(12)).toString())).getNumericCellValue();


                        incomestatement.setOnemonth(new BigDecimal(mon1).doubleValue());
                        incomestatement.setTwomonth(new BigDecimal(mon2).doubleValue());
                        incomestatement.setThreemonth(new BigDecimal(mon3).doubleValue());
                        incomestatement.setFourmonth(new BigDecimal(mon4).doubleValue());
                        incomestatement.setFivemonth(new BigDecimal(mon5).doubleValue());
                        incomestatement.setSixmonth(new BigDecimal(mon6).doubleValue());
                        incomestatement.setSevenmonth(new BigDecimal(mon7).doubleValue());
                        incomestatement.setEightmonth(new BigDecimal(mon8).doubleValue());
                        incomestatement.setNinemonth(new BigDecimal(mon9).doubleValue());
                        incomestatement.setTenmonth(new BigDecimal(mon10).doubleValue());
                        incomestatement.setEleventmonth(new BigDecimal(mon11).doubleValue());
                        incomestatement.setTwelvemonth(new BigDecimal(mon12).doubleValue());
                        incomestatement.setExcelname(ConstantUtil.incomeUploadEntity.getExcelname());
                        incomestatement.setYear(Long.parseLong(ConstantUtil.incomeUploadEntity.getYear()));

                        logger.info("开始插入车牌号" + sheetName + "title为" + rowName + "的数据");
                        incomeStatementDao.insert(incomestatement);
                        logger.info("车牌号" + sheetName + "title为" + rowName + "的数据插入成功");

                    }
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Incomestatement incomestatement, UpdateWrapper<Incomestatement> updateWrapper, ResponseEntity responseEntity) {

        int update = incomeStatementDao.update(incomestatement, updateWrapper);

        if (update != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预期修改效果");
            //未修改到想要数据 抛出异常，并回滚
            throw new RuntimeException("未达到预期修改效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("修改成功" + update + "条数据");

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(LambdaQueryWrapper<Incomestatement> queryWrapper, ResponseEntity responseEntity) {
        int delete = incomeStatementDao.delete(queryWrapper);

        if (delete != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预期删除效果");
            //未修改到想要数据 抛出异常，并回滚
            throw new RuntimeException("未达到预期删除效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("删除成功" + delete + "条数据");

        }
    }

    @Override
    public List<Incomestatement> querySumByColumn(String cartype,String year) {
        return incomeStatementDao.querySumByColumn(cartype,year);
    }

    @Override
    public List<IncomeExportEntity> exportIncomeAll(String cartype, String year) {
        return incomeStatementDao.exportIncomeAll(cartype,year);
    }
}
