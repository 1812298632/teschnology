package com.excel.pro.web;

import com.excel.pro.dao.UserDao;
import com.excel.pro.entity.*;
import com.excel.pro.service.DepartService;
import com.excel.pro.service.FuleService;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExcelPrintController {

    Logger logger = LoggerFactory.getLogger(ExcelPrintController.class);

    @Resource
    private UserDao userDao;


    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private DepartService departService;


    @Resource
    private FuleService fuleService;

    @GetMapping("/query")
    public String queryUser() {
        List<SysUser> sysUsers = userDao.selectList(null);
        return "hello";
    }

    /**
     * 导入发车明细中的所有数据
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/departdeatil")
    public String insertDepartDetail() throws IOException {
       /* LinkedList<String> sheetNameList = new LinkedList<>();
        sheetNameList.add("解放车3");
        sheetNameList.add("解放车4");
        sheetNameList.add("解放车5");
        sheetNameList.add("解放车6");
        sheetNameList.add("解放车7");
        sheetNameList.add("解放车8");
        sheetNameList.add("解放车9");
        sheetNameList.add("解放车10");
        sheetNameList.add("解放车11");
        sheetNameList.add("解放车12");
        sheetNameList.add("解放车1");
        sheetNameList.add("解放车2");*/

        //需要获取excel中sheet的数据，就将sheet名称放入到list中
        LinkedList<String> sheetNameList = ConstantUtil.makeSheetNameList();

        for (String s : sheetNameList) {
            departService.insertDepartDetail(s);

        }
        return "123";
    }


    @GetMapping("/departother")
    public String departother(String cartype, String sheetname) throws IOException {
       /* LinkedList<String> sheetNameList = new LinkedList<>();
        sheetNameList.add("解放车3");
        sheetNameList.add("解放车4");
        sheetNameList.add("解放车5");
        sheetNameList.add("解放车6");
        sheetNameList.add("解放车7");
        sheetNameList.add("解放车8");
        sheetNameList.add("解放车9");
        sheetNameList.add("解放车10");
        sheetNameList.add("解放车11");
        sheetNameList.add("解放车12");
        sheetNameList.add("解放车1");
        sheetNameList.add("解放车2");*/

        //需要获取excel中sheet的数据，就将sheet名称放入到list中

        //需要获取excel中sheet的数据，就将sheet名称放入到list中
        LinkedList<String> sheetNameList = ConstantUtil.makeSheetNameOthrList();
        int month = 2;
        for (String s : sheetNameList) {
            month++;
            departService.insertDepartother(s, month);
        }


        return "123";
    }


    /**
     * 导入损益表中的所有数据
     *
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping("/statement")
    public String insertIncomeStatement() throws FileNotFoundException {
      /*  LinkedList<String> sheetNameList = new LinkedList<>();
        //需要获取excel中sheet的数据，就将sheet名称放入到list中
        sheetNameList.add("苏BB0057");
        sheetNameList.add("苏BX6615");
        sheetNameList.add("苏BX6667");
        sheetNameList.add("苏BX6609");
        sheetNameList.add("苏B98997");
        sheetNameList.add("苏BB5979（新）");
        sheetNameList.add("沃尔沃");
        sheetNameList.add("解放车");*/

        LinkedList<String> sheetNameList = ConstantUtil.makeIncomeSheetNameList();
        for (String s : sheetNameList) {
            incomeStatementService.insertIncomeStatement(s);
        }

        return "success";
    }


    /**
     * 导出发车次数
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/exportDepart")
    public ResponseEntity exportDepart(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseEntity responseEntity = new ResponseEntity();

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");

        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "发车明细]");

        LinkedList<DepartExportEntity> countList = new LinkedList<>();

        LinkedList<String> cityList = ConstantUtil.makeCityNameRowTitle();


        for (String cityname : cityList) {
            //根据城市，查询出每个城市每个月份的发车次数
            DepartExportEntity exportDepart = departService.exportDepart(cityname, cartype, year);

            countList.add(exportDepart);
        }

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "发车明细");

        LinkedList<String> titleList = ConstantUtil.makeDepartTitle();

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < countList.size(); i++) {
            row = sheet.createRow(i + 1);
            DepartExportEntity exportEntity = countList.get(i);

            String[] countListArray = {exportEntity.getCity(), exportEntity.getThree(),
                    exportEntity.getFour(), exportEntity.getFive(), exportEntity.getSix(), exportEntity.getSeven(), exportEntity.getEight(),
                    exportEntity.getNine(), exportEntity.getTen(), exportEntity.getEleven(), exportEntity.getTwelve(), exportEntity.getOne(),
                    exportEntity.getTwo(), exportEntity.getAllcount()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                if (j == 0) {
                    row.createCell((short) j).setCellValue(countListArray[j]);

                } else {
                    row.createCell((short) j).setCellValue(Integer.parseInt(countListArray[j]));
                }
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\ " + cartype + "发车明细.xlsx");

            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }


        return responseEntity;
    }


    /**
     * 导出 解放车/沃尔沃 过路费 油费  罚款 停车费 轮胎费 维修费
     *
     * @return
     */
    @RequestMapping("/exportIncomeData")
    public ResponseEntity exportIncomeData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
        String year = RequestUtil.getObjectValue(requestParam, "year");

        List<IncomeExportEntity> exportEntityList = null;

        if (!cartype.equals("总损益表")) {
            exportEntityList = incomeStatementService.exportIncome(cartype, year);
        } else {
            exportEntityList = incomeStatementService.exportIncomeAll(cartype, year);
        }


        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + columnname.trim() + "]");

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype, year);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(columnname)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        exportEntityList.forEach(x -> {
            BigDecimal runcount = new BigDecimal(Double.toString(x.getRuncount()));
            BigDecimal kilosum = new BigDecimal(x.getKilosum());
            BigDecimal runcountmoney = new BigDecimal(Double.toString(x.getMoney()));
            BigDecimal kilosummoney = new BigDecimal(Double.toString(x.getMoney()));

            x.setAvgonway(runcountmoney.divide(runcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
            x.setCost(kilosummoney.divide(kilosum, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        });


        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();
        IncomeExportEntity incomeExportEntity = new IncomeExportEntity();

        incomeExportEntity.setMonth("总和");

        List<Incomestatement> incomestatement = incomeStatementService.querySumByColumn(cartype, year);

        Incomestatement summoney = incomestatement.stream().filter(x -> x.getColumnname().equals(columnname)).collect(Collectors.toList()).get(0);

        incomeExportEntity.setMoney(Double.parseDouble(summoney.getSubjectcode()));


        Double kiloSum = 0.00;
        Double cost = 0.00;
        Double avg = 0.00;
        Double runCount = 0.00;
        Double avgonway = 0.00;


        for (IncomeExportEntity x : exportEntityList) {
            kiloSum = Double.parseDouble(x.getKilosum()) + kiloSum;
            runCount = x.getRuncount() + runCount;
            avg = x.getAvg() + avg;
            avgonway = x.getAvgonway() + avgonway;
            cost = x.getCost() + cost;
        }

        incomeExportEntity.setKilosum(kiloSum.toString());
        incomeExportEntity.setRuncount(runCount);
        incomeExportEntity.setAvg(avg);
        incomeExportEntity.setAvgonway(avgonway);
        incomeExportEntity.setCost(cost);


        IncomeExportEntity incomeExportEntityAvg = new IncomeExportEntity();

        incomeExportEntityAvg.setMonth("均值");
        BigDecimal monthcount = new BigDecimal(exportEntityList.size());
        incomeExportEntityAvg.setKilosum(new BigDecimal(incomeExportEntity.getKilosum()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).toString());
        incomeExportEntityAvg.setRuncount(new BigDecimal(incomeExportEntity.getRuncount()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setAvg(new BigDecimal(incomeExportEntity.getAvg()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setMoney(new BigDecimal(incomeExportEntity.getMoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setAvgonway(new BigDecimal(incomeExportEntity.getAvgonway()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setCost(new BigDecimal(incomeExportEntity.getCost()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());


        exportEntityList.add(incomeExportEntity);
        exportEntityList.add(incomeExportEntityAvg);


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + columnname.trim());

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        //CellStyle cellStyle = wb.createCellStyle();
        //cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));


        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(),
                    exportEntity.getAvg().toString(), exportEntity.getMoney().toString(), exportEntity.getAvgonway().toString(), exportEntity.getCost().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                HSSFCell cell = row.createCell((short) j);
                if (row.getRowNum() <= exportEntityList.size() - 2) {
                    if (j <= 2) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }
                } else {
                    if (j == 0) {
                        cell.setCellValue(countListArray[j]);
                    } else if (j <= 2 && j != 0) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }

                }


            }
        }


        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + columnname.trim() + ".xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }


        return responseEntity;
    }


    @RequestMapping("/exportYh")
    public ResponseEntity exporYh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String columnname = RequestUtil.getObjectValue(requestParam, "columnname");
        String year = RequestUtil.getObjectValue(requestParam, "year");

        List<IncomeExportEntity> exportEntityList = null;

        if (!cartype.equals("总损益表")) {
            exportEntityList = incomeStatementService.exportIncome(cartype, year);
        } else {
            exportEntityList = incomeStatementService.exportIncomeAll(cartype, year);
        }


        Incomestatement monthMoney = fuleService.queryMonthMoney(year, cartype, "加油升数");

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);


        Incomestatement monthSaveMoney = fuleService.queryMonthMoney(year, cartype, "节油升数");

        HashMap<Object, Object> hashSaveMap = new HashMap<>();
        hashSaveMap.put("1", monthSaveMoney.getOnemonth());
        hashSaveMap.put("2", monthSaveMoney.getTwomonth());
        hashSaveMap.put("3", monthSaveMoney.getThreemonth());
        hashSaveMap.put("4", monthSaveMoney.getFourmonth());
        hashSaveMap.put("5", monthSaveMoney.getFivemonth());
        hashSaveMap.put("6", monthSaveMoney.getSixmonth());
        hashSaveMap.put("7", monthSaveMoney.getSevenmonth());
        hashSaveMap.put("8", monthSaveMoney.getEightmonth());
        hashSaveMap.put("9", monthSaveMoney.getNinemonth());
        hashSaveMap.put("10", monthSaveMoney.getTenmonth());
        hashSaveMap.put("11", monthSaveMoney.getEleventmonth());
        hashSaveMap.put("12", monthSaveMoney.getTwelvemonth());


        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype, year);

        Incomestatement fuleMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title3)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashFuleMoneyMap = new HashMap<>();
        hashFuleMoneyMap.put("1", fuleMoney.getOnemonth());
        hashFuleMoneyMap.put("2", fuleMoney.getTwomonth());
        hashFuleMoneyMap.put("3", fuleMoney.getThreemonth());
        hashFuleMoneyMap.put("4", fuleMoney.getFourmonth());
        hashFuleMoneyMap.put("5", fuleMoney.getFivemonth());
        hashFuleMoneyMap.put("6", fuleMoney.getSixmonth());
        hashFuleMoneyMap.put("7", fuleMoney.getSevenmonth());
        hashFuleMoneyMap.put("8", fuleMoney.getEightmonth());
        hashFuleMoneyMap.put("9", fuleMoney.getNinemonth());
        hashFuleMoneyMap.put("10", fuleMoney.getTenmonth());
        hashFuleMoneyMap.put("11", fuleMoney.getEleventmonth());
        hashFuleMoneyMap.put("12", fuleMoney.getTwelvemonth());


        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype +"百公里油耗" + "]");

        exportEntityList.forEach(x -> {
            x.setFuelingLiters(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
            x.setFuelSavingLiters(Double.parseDouble(hashSaveMap.get(x.getMonth()).toString()));
            x.setFuelmoney(Double.parseDouble(hashFuleMoneyMap.get(x.getMonth()).toString()));
        });

        exportEntityList.forEach(x -> {
            BigDecimal fulemoney = new BigDecimal(Double.toString(x.getFuelmoney()));
            BigDecimal kilosum = new BigDecimal(x.getKilosum());
            BigDecimal fuleLit = new BigDecimal(Double.toString(x.getFuelingLiters()));
            BigDecimal fuleSaveLit = new BigDecimal(Double.toString(x.getFuelSavingLiters()));

            BigDecimal sumMonth = fuleLit.divide(fulemoney, 20, BigDecimal.ROUND_HALF_UP);

            x.setHundredFule(fuleLit.divide(kilosum, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
            x.setAvgonway(sumMonth.doubleValue());
            x.setFuelSavingMoney(sumMonth.multiply(fuleSaveLit).setScale(20,BigDecimal.ROUND_HALF_UP).doubleValue());
            //x.setFuelSavingLiters(fulemoney.divide(fuleSaveLit, 20, BigDecimal.ROUND_HALF_UP).setScale(20,BigDecimal.ROUND_HALF_UP).doubleValue());

            x.setKilosum("");
        });


        IncomeExportEntity incomeExportEntity = new IncomeExportEntity();


        incomeExportEntity.setMonth("总和");

        List<Incomestatement> incomestatement = incomeStatementService.querySumByColumn(cartype, year);

        Incomestatement summoney = incomestatement.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title3)).collect(Collectors.toList()).get(0);


        List<Incomestatement> sumFuleMoney = fuleService.queryFuleSum(cartype, year);

        BigDecimal fuelFitSum = new BigDecimal(sumFuleMoney.stream().filter(x -> x.getColumnname().equals("加油升数")).collect(Collectors.toList()).get(0).getSubjectcode());
        BigDecimal fuelSaveFitSum = new BigDecimal(sumFuleMoney.stream().filter(x -> x.getColumnname().equals("节油升数")).collect(Collectors.toList()).get(0).getSubjectcode());
        String kiloSum = fuleService.getkiloSum(cartype, year);
        BigDecimal monthcount = new BigDecimal(exportEntityList.size());
        BigDecimal fulemoneySum = new BigDecimal(summoney.getSubjectcode());

        BigDecimal sumMonth = fulemoneySum.divide(fuelFitSum, 20, BigDecimal.ROUND_HALF_UP);
        incomeExportEntity.setFuelingLiters(fuelFitSum.doubleValue());
        incomeExportEntity.setFuelSavingLiters(fuelSaveFitSum.doubleValue());
        incomeExportEntity.setKilosum(kiloSum);
        incomeExportEntity.setFuelmoney(fulemoneySum.doubleValue());
        incomeExportEntity.setFuelSavingMoney(sumMonth.multiply(fuelSaveFitSum).setScale(20,BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntity.setHundredFule(fuelFitSum.divide(new BigDecimal(kiloSum), 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntity.setAvgonway(sumMonth.doubleValue());
        IncomeExportEntity incomeExportEntityAvg = new IncomeExportEntity();

        incomeExportEntityAvg.setMonth("均值");




        fulemoneySum = new BigDecimal(summoney.getSubjectcode()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP);


        incomeExportEntityAvg.setKilosum(new BigDecimal(incomeExportEntity.getKilosum()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).toString());
        incomeExportEntityAvg.setFuelingLiters(new BigDecimal(incomeExportEntity.getFuelingLiters()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setFuelSavingLiters(new BigDecimal(incomeExportEntity.getFuelSavingLiters()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setFuelmoney(new BigDecimal(incomeExportEntity.getFuelmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeExportEntityAvg.setHundredFule(fuelFitSum.divide(new BigDecimal(kiloSum), 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        BigDecimal avgMonth = fulemoneySum.divide(fuelFitSum, 20, BigDecimal.ROUND_HALF_UP).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP);

        incomeExportEntityAvg.setAvgonway(sumMonth.doubleValue());
        incomeExportEntityAvg.setFuelSavingMoney(new BigDecimal(incomeExportEntity.getFuelSavingMoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());

        exportEntityList.add(incomeExportEntity);
        exportEntityList.add(incomeExportEntityAvg);

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "百公里油耗");

        LinkedList<String> titleList = ConstantUtil.makeYhTitle();

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getFuelingLiters().toString(), exportEntity.getKilosum(),
                    exportEntity.getHundredFule().toString(), exportEntity.getFuelSavingLiters().toString(), exportEntity.getAvgonway().toString(), exportEntity.getFuelSavingMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                HSSFCell cell = row.createCell((short) j);
                if(countListArray[j].equals("")){
                    continue;
                }
                if (row.getRowNum() <= exportEntityList.size() - 2) {
                    if (j <= 2) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }
                } else {
                    if (j == 0) {
                        cell.setCellValue(countListArray[j]);
                    } else if (j <= 2 && j != 0) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }

                }


            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "百公里油耗.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }


        return responseEntity;
    }


    /**
     * 过路费
     *
     * @return
     */
   /* @RequestMapping("/exportIncomeTolls")
    public ResponseEntity exportIncomeTolls(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);


        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "过路费]");

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title4)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "过路费");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }


        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "过路费.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }


        return responseEntity;
    }


    *//**
     * 燃油费
     *
     * @return
     *//*
    @RequestMapping("/exportIncomeFuel")
    public ResponseEntity exportIncomeFuel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseEntity responseEntity = new ResponseEntity();

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "燃油费]");

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title3)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "燃油费");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }


        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "燃油费.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }


        return responseEntity;

    }


    *//**
     * 罚款
     *
     * @return
     *//*
    @RequestMapping("/exportIncomeFine")
    public ResponseEntity exportIncomeFine(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "罚款]");


        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title6)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "罚款");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "罚款.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }

        return responseEntity;
    }


    *//**
     * 停车费
     *
     * @return
     *//*
    @RequestMapping("/exportIncomeParking")
    public ResponseEntity exportIncomeParking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "停车费]");

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title5)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "停车费");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "停车费.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }
        return responseEntity;
    }


    *//**
     * 轮胎费
     *
     * @return
     *//*
    @RequestMapping("/exportIncomeTire")
    public ResponseEntity exportIncomeTire(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "轮胎费]");


        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title9)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "轮胎费");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "轮胎费.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }

        return responseEntity;
    }


    *//**
     * 维修费
     *
     * @return
     *//*
    @RequestMapping("/exportIncomeRepair")
    public ResponseEntity exportIncomeRepair(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();
        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome(cartype);

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "维修费]");

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title8)).collect(Collectors.toList()).get(0);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", monthMoney.getOnemonth());
        hashMap.put("2", monthMoney.getTwomonth());
        hashMap.put("3", monthMoney.getThreemonth());
        hashMap.put("4", monthMoney.getFourmonth());
        hashMap.put("5", monthMoney.getFivemonth());
        hashMap.put("6", monthMoney.getSixmonth());
        hashMap.put("7", monthMoney.getSevenmonth());
        hashMap.put("8", monthMoney.getEightmonth());
        hashMap.put("9", monthMoney.getNinemonth());
        hashMap.put("10", monthMoney.getTenmonth());
        hashMap.put("11", monthMoney.getEleventmonth());
        hashMap.put("12", monthMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setMoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        LinkedList<String> titleList = ConstantUtil.makeToolsTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "维修费");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(), exportEntity.getAvg().toString(), exportEntity.getMoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "维修费.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }

        return responseEntity;
    }*/


    /**
     * 毛利
     *
     * @return
     */
    @RequestMapping("/exportIncomeGross")
    public ResponseEntity exportIncomeGross(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();

        String requestParam = RequestUtil.getJsonObjectData(request);
        String cartype = RequestUtil.getObjectValue(requestParam, "cartype");
        String year = RequestUtil.getObjectValue(requestParam, "year");
        List<IncomeExportEntity> exportEntityList = null;

        if (!cartype.equals("总损益表")) {
            exportEntityList = incomeStatementService.exportIncome(cartype, year);
        } else {
            exportEntityList = incomeStatementService.exportIncomeAll(cartype, year);
        }

        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        responseEntity.setResMessage("文件生成成功,生成文件名称为[" + cartype + "毛利]");
        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype, year);

        Incomestatement totalincomeMoney = null;

        //毛利表中 车队收入列
        //2021
        //Incomestatement totalincomeMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title1)).collect(Collectors.toList()).get(0);

        //2022
        if (year.equals("2022")) {
            totalincomeMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title16)).collect(Collectors.toList()).get(0);
        } else if (year.equals("2021")) {
            totalincomeMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title1)).collect(Collectors.toList()).get(0);
        }


        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("1", totalincomeMoney.getOnemonth());
        hashMap.put("2", totalincomeMoney.getTwomonth());
        hashMap.put("3", totalincomeMoney.getThreemonth());
        hashMap.put("4", totalincomeMoney.getFourmonth());
        hashMap.put("5", totalincomeMoney.getFivemonth());
        hashMap.put("6", totalincomeMoney.getSixmonth());
        hashMap.put("7", totalincomeMoney.getSevenmonth());
        hashMap.put("8", totalincomeMoney.getEightmonth());
        hashMap.put("9", totalincomeMoney.getNinemonth());
        hashMap.put("10", totalincomeMoney.getTenmonth());
        hashMap.put("11", totalincomeMoney.getEleventmonth());
        hashMap.put("12", totalincomeMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setTotalincomemoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 过路费列
        Incomestatement tollsMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title4)).collect(Collectors.toList()).get(0);


        hashMap.clear();
        hashMap.put("1", tollsMoney.getOnemonth());
        hashMap.put("2", tollsMoney.getTwomonth());
        hashMap.put("3", tollsMoney.getThreemonth());
        hashMap.put("4", tollsMoney.getFourmonth());
        hashMap.put("5", tollsMoney.getFivemonth());
        hashMap.put("6", tollsMoney.getSixmonth());
        hashMap.put("7", tollsMoney.getSevenmonth());
        hashMap.put("8", tollsMoney.getEightmonth());
        hashMap.put("9", tollsMoney.getNinemonth());
        hashMap.put("10", tollsMoney.getTenmonth());
        hashMap.put("11", tollsMoney.getEleventmonth());
        hashMap.put("12", tollsMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setTollsmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 燃油费列
        Incomestatement fuelMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title3)).collect(Collectors.toList()).get(0);


        hashMap.clear();
        hashMap.put("1", fuelMoney.getOnemonth());
        hashMap.put("2", fuelMoney.getTwomonth());
        hashMap.put("3", fuelMoney.getThreemonth());
        hashMap.put("4", fuelMoney.getFourmonth());
        hashMap.put("5", fuelMoney.getFivemonth());
        hashMap.put("6", fuelMoney.getSixmonth());
        hashMap.put("7", fuelMoney.getSevenmonth());
        hashMap.put("8", fuelMoney.getEightmonth());
        hashMap.put("9", fuelMoney.getNinemonth());
        hashMap.put("10", fuelMoney.getTenmonth());
        hashMap.put("11", fuelMoney.getEleventmonth());
        hashMap.put("12", fuelMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setFuelmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 罚款列
        Incomestatement finesMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title6)).collect(Collectors.toList()).get(0);

        hashMap.clear();
        hashMap.put("1", finesMoney.getOnemonth());
        hashMap.put("2", finesMoney.getTwomonth());
        hashMap.put("3", finesMoney.getThreemonth());
        hashMap.put("4", finesMoney.getFourmonth());
        hashMap.put("5", finesMoney.getFivemonth());
        hashMap.put("6", finesMoney.getSixmonth());
        hashMap.put("7", finesMoney.getSevenmonth());
        hashMap.put("8", finesMoney.getEightmonth());
        hashMap.put("9", finesMoney.getNinemonth());
        hashMap.put("10", finesMoney.getTenmonth());
        hashMap.put("11", finesMoney.getEleventmonth());
        hashMap.put("12", finesMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setFinesmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 停车费列
        Incomestatement parkingmoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title5)).collect(Collectors.toList()).get(0);

        hashMap.clear();
        hashMap.put("1", parkingmoney.getOnemonth());
        hashMap.put("2", parkingmoney.getTwomonth());
        hashMap.put("3", parkingmoney.getThreemonth());
        hashMap.put("4", parkingmoney.getFourmonth());
        hashMap.put("5", parkingmoney.getFivemonth());
        hashMap.put("6", parkingmoney.getSixmonth());
        hashMap.put("7", parkingmoney.getSevenmonth());
        hashMap.put("8", parkingmoney.getEightmonth());
        hashMap.put("9", parkingmoney.getNinemonth());
        hashMap.put("10", parkingmoney.getTenmonth());
        hashMap.put("11", parkingmoney.getEleventmonth());
        hashMap.put("12", parkingmoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setParkingmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 轮胎费列
        Incomestatement tiremoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title9)).collect(Collectors.toList()).get(0);

        hashMap.clear();
        hashMap.put("1", tiremoney.getOnemonth());
        hashMap.put("2", tiremoney.getTwomonth());
        hashMap.put("3", tiremoney.getThreemonth());
        hashMap.put("4", tiremoney.getFourmonth());
        hashMap.put("5", tiremoney.getFivemonth());
        hashMap.put("6", tiremoney.getSixmonth());
        hashMap.put("7", tiremoney.getSevenmonth());
        hashMap.put("8", tiremoney.getEightmonth());
        hashMap.put("9", tiremoney.getNinemonth());
        hashMap.put("10", tiremoney.getTenmonth());
        hashMap.put("11", tiremoney.getEleventmonth());
        hashMap.put("12", tiremoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setTiremoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 维修费列
        Incomestatement repairmoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title8)).collect(Collectors.toList()).get(0);

        hashMap.clear();
        hashMap.put("1", repairmoney.getOnemonth());
        hashMap.put("2", repairmoney.getTwomonth());
        hashMap.put("3", repairmoney.getThreemonth());
        hashMap.put("4", repairmoney.getFourmonth());
        hashMap.put("5", repairmoney.getFivemonth());
        hashMap.put("6", repairmoney.getSixmonth());
        hashMap.put("7", repairmoney.getSevenmonth());
        hashMap.put("8", repairmoney.getEightmonth());
        hashMap.put("9", repairmoney.getNinemonth());
        hashMap.put("10", repairmoney.getTenmonth());
        hashMap.put("11", repairmoney.getEleventmonth());
        hashMap.put("12", repairmoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setRepairmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 其他列
        ArrayList<String> otherRowTitleList = new ArrayList<>();
        otherRowTitleList.add(ConstantUtil.title13);
        otherRowTitleList.add(ConstantUtil.title11);

        Incomestatement otherMoney = incomeStatementService.queryMonthMoneyOther(cartype, year, otherRowTitleList);

        hashMap.clear();
        hashMap.put("1", otherMoney.getOnemonth());
        hashMap.put("2", otherMoney.getTwomonth());
        hashMap.put("3", otherMoney.getThreemonth());
        hashMap.put("4", otherMoney.getFourmonth());
        hashMap.put("5", otherMoney.getFivemonth());
        hashMap.put("6", otherMoney.getSixmonth());
        hashMap.put("7", otherMoney.getSevenmonth());
        hashMap.put("8", otherMoney.getEightmonth());
        hashMap.put("9", otherMoney.getNinemonth());
        hashMap.put("10", otherMoney.getTenmonth());
        hashMap.put("11", otherMoney.getEleventmonth());
        hashMap.put("12", otherMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setOthermoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 人工成本
        ArrayList<String> manRowTitleList = new ArrayList<>();
        otherRowTitleList.add(ConstantUtil.title13);
        otherRowTitleList.add(ConstantUtil.title11);

        Incomestatement manMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title20)).collect(Collectors.toList()).get(0);


        hashMap.clear();
        hashMap.put("1", manMoney.getOnemonth());
        hashMap.put("2", manMoney.getTwomonth());
        hashMap.put("3", manMoney.getThreemonth());
        hashMap.put("4", manMoney.getFourmonth());
        hashMap.put("5", manMoney.getFivemonth());
        hashMap.put("6", manMoney.getSixmonth());
        hashMap.put("7", manMoney.getSevenmonth());
        hashMap.put("8", manMoney.getEightmonth());
        hashMap.put("9", manMoney.getNinemonth());
        hashMap.put("10", manMoney.getTenmonth());
        hashMap.put("11", manMoney.getEleventmonth());
        hashMap.put("12", manMoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setManmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });


        //毛利表中 总成本列  如果总成本是前面几个费用相加 用这个
        ArrayList<String> otherRowTitleList1 = new ArrayList<>();
        otherRowTitleList1.add(ConstantUtil.title13);
        otherRowTitleList1.add(ConstantUtil.title11);
        otherRowTitleList1.add(ConstantUtil.title4);
        otherRowTitleList1.add(ConstantUtil.title3);
        otherRowTitleList1.add(ConstantUtil.title6);
        otherRowTitleList1.add(ConstantUtil.title5);
        otherRowTitleList1.add(ConstantUtil.title9);
        otherRowTitleList1.add(ConstantUtil.title8);
        otherRowTitleList1.add(ConstantUtil.title20);

        Incomestatement totalcostmoney = incomeStatementService.queryMonthMoneyOther(cartype, year, otherRowTitleList1);

        hashMap.clear();
        hashMap.put("1", totalcostmoney.getOnemonth());
        hashMap.put("2", totalcostmoney.getTwomonth());
        hashMap.put("3", totalcostmoney.getThreemonth());
        hashMap.put("4", totalcostmoney.getFourmonth());
        hashMap.put("5", totalcostmoney.getFivemonth());
        hashMap.put("6", totalcostmoney.getSixmonth());
        hashMap.put("7", totalcostmoney.getSevenmonth());
        hashMap.put("8", totalcostmoney.getEightmonth());
        hashMap.put("9", totalcostmoney.getNinemonth());
        hashMap.put("10", totalcostmoney.getTenmonth());
        hashMap.put("11", totalcostmoney.getEleventmonth());
        hashMap.put("12", totalcostmoney.getTwelvemonth());


        exportEntityList.forEach(x -> {
            x.setTotalcostmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });

        //毛利表中 总成本列  如果总成本是损溢表中总成本 用这个
       /* Incomestatement totalcostmoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title14)).collect(Collectors.toList()).get(0);

        hashMap.clear();
        hashMap.put("1", totalcostmoney.getOnemonth());
        hashMap.put("2", totalcostmoney.getTwomonth());
        hashMap.put("3", totalcostmoney.getThreemonth());
        hashMap.put("4", totalcostmoney.getFourmonth());
        hashMap.put("5", totalcostmoney.getFivemonth());
        hashMap.put("6", totalcostmoney.getSixmonth());
        hashMap.put("7", totalcostmoney.getSevenmonth());
        hashMap.put("8", totalcostmoney.getEightmonth());
        hashMap.put("9", totalcostmoney.getNinemonth());
        hashMap.put("10", totalcostmoney.getTenmonth());
        hashMap.put("11", totalcostmoney.getEleventmonth());
        hashMap.put("12", totalcostmoney.getTwelvemonth());

        exportEntityList.forEach(x -> {
            x.setTotalcostmoney(Double.parseDouble(hashMap.get(x.getMonth()).toString()));
        });*/


        exportEntityList.forEach(x -> {
            BigDecimal totalcsot = new BigDecimal(Double.toString(x.getTotalcostmoney()));//总成本
            BigDecimal Kilosum = new BigDecimal(x.getKilosum());//运行总里程
            BigDecimal totalincomemoney = new BigDecimal(Double.toString(x.getTotalincomemoney()));//总收入
            BigDecimal runcount = new BigDecimal(Double.toString(x.getRuncount()));//单程

            //计算公里成本费用
            BigDecimal kilocost = totalcsot.divide(Kilosum, 20, BigDecimal.ROUND_HALF_UP);
            x.setKilocost(kilocost.doubleValue());

            //计算毛利
            BigDecimal gross = totalincomemoney.subtract(totalcsot);
            x.setGross(gross.doubleValue());

            if (gross.compareTo(BigDecimal.ZERO) != 0) {
                if (totalincomemoney.compareTo(BigDecimal.ZERO) != 0) {
                    //计算毛利率
                    BigDecimal grossrate = gross.divide(totalincomemoney, 20, BigDecimal.ROUND_HALF_UP);
                    x.setGrossrate(grossrate.doubleValue());
                } else {
                    x.setGrossrate(0.00);
                }

                if (runcount.compareTo(BigDecimal.ZERO) != 0) {
                    //计算单程毛利
                    BigDecimal grossonway = gross.divide(runcount, 20, BigDecimal.ROUND_HALF_UP);
                    x.setGrossonway(grossonway.doubleValue());
                } else {
                    x.setGrossonway(0.00);
                }
            }

            //计算每公里收入
            x.setKiloincome(totalincomemoney.divide(Kilosum, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        });

        Double totalRuncount = 0.00;
        Double totalkilosum = 0.00;
        Double totalIncomeMoney = 0.00;
        Double totalkiloIncome = 0.00;
        Double totalTollMoney = 0.00;
        Double totalFuelMoney = 0.00;
        Double totalFineMoney = 0.00;
        Double totalParkingMoney = 0.00;
        Double totalTireMoney = 0.00;
        Double totalRepairMoney = 0.00;
        Double totalManMoney = 0.00;
        Double totalOtherMoney = 0.00;
        Double totalcostmoney1 = 0.00;

        IncomeExportEntity totalIncomeEntity = new IncomeExportEntity();

        for (IncomeExportEntity x : exportEntityList) {
            totalRuncount = x.getRuncount() + totalRuncount;
            totalkilosum = Double.parseDouble(x.getKilosum()) + totalkilosum;
            totalIncomeMoney = x.getTotalincomemoney() + totalIncomeMoney;
            totalkiloIncome = x.getKiloincome() + totalkiloIncome;
            totalTollMoney = x.getTollsmoney() + totalTollMoney;
            totalFuelMoney = x.getFuelmoney() + totalFuelMoney;
            totalFineMoney = x.getFinesmoney() + totalFineMoney;
            totalParkingMoney = x.getParkingmoney() + totalParkingMoney;
            totalTireMoney = x.getTiremoney() + totalTireMoney;
            totalRepairMoney = x.getRepairmoney() + totalRepairMoney;
            totalManMoney = x.getManmoney() + totalManMoney;
            totalOtherMoney = x.getOthermoney() + totalOtherMoney;
            totalcostmoney1 = x.getTotalcostmoney() + totalcostmoney1;
        }
        totalIncomeEntity.setRuncount(totalRuncount);
        totalIncomeEntity.setKilosum(totalkilosum.toString());
        totalIncomeEntity.setTotalincomemoney(totalIncomeMoney);
        totalIncomeEntity.setKiloincome(totalkiloIncome);
        totalIncomeEntity.setTollsmoney(totalTollMoney);
        totalIncomeEntity.setFuelmoney(totalFuelMoney);
        totalIncomeEntity.setFinesmoney(totalFineMoney);
        totalIncomeEntity.setParkingmoney(totalParkingMoney);
        totalIncomeEntity.setTiremoney(totalTireMoney);
        totalIncomeEntity.setRepairmoney(totalRepairMoney);
        totalIncomeEntity.setManmoney(totalManMoney);
        totalIncomeEntity.setOthermoney(totalOtherMoney);
        totalIncomeEntity.setTotalcostmoney(totalcostmoney1);


        BigDecimal monthcount = new BigDecimal(exportEntityList.size());
        IncomeExportEntity incomeEntityAvg = new IncomeExportEntity();

        incomeEntityAvg.setKilosum(new BigDecimal(totalIncomeEntity.getKilosum()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).toString());
        incomeEntityAvg.setRuncount(new BigDecimal(totalIncomeEntity.getRuncount()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setTotalincomemoney(new BigDecimal(totalIncomeEntity.getTotalincomemoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setKiloincome(new BigDecimal(totalIncomeEntity.getKiloincome()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setTollsmoney(new BigDecimal(totalIncomeEntity.getTollsmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setFuelmoney(new BigDecimal(totalIncomeEntity.getFuelmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setFinesmoney(new BigDecimal(totalIncomeEntity.getFinesmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setParkingmoney(new BigDecimal(totalIncomeEntity.getParkingmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setTiremoney(new BigDecimal(totalIncomeEntity.getTiremoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setRepairmoney(new BigDecimal(totalIncomeEntity.getRepairmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setManmoney(new BigDecimal(totalIncomeEntity.getManmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setOthermoney(new BigDecimal(totalIncomeEntity.getOthermoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());
        incomeEntityAvg.setTotalcostmoney(new BigDecimal(totalIncomeEntity.getTotalcostmoney()).divide(monthcount, 20, BigDecimal.ROUND_HALF_UP).doubleValue());


        BigDecimal totalcsot = new BigDecimal(Double.toString(incomeEntityAvg.getTotalcostmoney()));//总成本
        BigDecimal Kilosum = new BigDecimal(incomeEntityAvg.getKilosum());//运行总里程
        BigDecimal totalincomemoney = new BigDecimal(Double.toString(incomeEntityAvg.getTotalincomemoney()));//总收入
        BigDecimal runcount = new BigDecimal(Double.toString(incomeEntityAvg.getRuncount()));//单程

        //计算公里成本费用
        BigDecimal kilocost = totalcsot.divide(Kilosum, 20, BigDecimal.ROUND_HALF_UP);
        incomeEntityAvg.setKilocost(kilocost.doubleValue());

        //计算毛利
        BigDecimal gross = totalincomemoney.subtract(totalcsot);
        incomeEntityAvg.setGross(gross.doubleValue());

        if (gross.compareTo(BigDecimal.ZERO) != 0) {
            if (totalincomemoney.compareTo(BigDecimal.ZERO) != 0) {
                //计算毛利率
                BigDecimal grossrate = gross.divide(totalincomemoney, 20, BigDecimal.ROUND_HALF_UP);
                incomeEntityAvg.setGrossrate(grossrate.doubleValue());
            } else {
                incomeEntityAvg.setGrossrate(0.00);
            }

            if (runcount.compareTo(BigDecimal.ZERO) != 0) {
                //计算单程毛利
                BigDecimal grossonway = gross.divide(runcount, 20, BigDecimal.ROUND_HALF_UP);
                incomeEntityAvg.setGrossonway(grossonway.doubleValue());
            } else {
                incomeEntityAvg.setGrossonway(0.00);
            }
        }

        //计算每公里收入
        incomeEntityAvg.setKiloincome(totalincomemoney.divide(Kilosum, 20, BigDecimal.ROUND_HALF_UP).doubleValue());

        incomeEntityAvg.setMonth("均值");

        exportEntityList.add(incomeEntityAvg);

        LinkedList<String> titleList = ConstantUtil.makeGrossTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(cartype + "毛利");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(),
                    exportEntity.getTotalincomemoney().toString(), exportEntity.getKiloincome().toString(), exportEntity.getTollsmoney().toString(), exportEntity.getFuelmoney().toString(), exportEntity.getFinesmoney().toString(),
                    exportEntity.getParkingmoney().toString(), exportEntity.getTiremoney().toString(), exportEntity.getRepairmoney().toString(), exportEntity.getManmoney().toString(), exportEntity.getOthermoney().toString(),
                    exportEntity.getTotalcostmoney().toString(), exportEntity.getKilocost().toString(), exportEntity.getGross().toString(), exportEntity.getGrossrate().toString(),
                    exportEntity.getGrossonway().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                HSSFCell cell = row.createCell((short) j);
                if (row.getRowNum() <= exportEntityList.size() - 2) {
                    if (j <= 2) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }
                } else {
                    if (j == 0) {
                        cell.setCellValue(countListArray[j]);
                    } else if (j <= 2 && j != 0) {
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    } else {
                        //cell.setCellStyle(cellStyle);
                        cell.setCellValue(Double.parseDouble(countListArray[j]));
                    }

                }
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\" + cartype + "毛利.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (responseEntity.getRes().equals(ConstantUtil.RESPONSE_SUCCESS)) {
            File f1 = new File("E:\\");
            Desktop.getDesktop().open(f1);
        }

        return responseEntity;
    }
}
