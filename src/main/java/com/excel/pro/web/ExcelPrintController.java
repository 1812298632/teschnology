package com.excel.pro.web;

import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.UserDao;
import com.excel.pro.entity.*;
import com.excel.pro.service.DepartService;
import com.excel.pro.service.IncomeStatementService;
import com.excel.pro.util.ConstantUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ExcelPrintController {

    Logger logger = LoggerFactory.getLogger(ExcelPrintController.class);

    @Resource
    private UserDao userDao;


    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private DepartService departService;

    @GetMapping("/query")
    public String queryUser() {
        List<SysUser> sysUsers = userDao.selectList(null);
        return "test";
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
    @GetMapping("/exportDepart")
    public String exportDepart() throws IOException {


        LinkedList<DepartExportEntity> countList = new LinkedList<>();

        LinkedList<String> cityList = ConstantUtil.makeCityNameRowTitle();


        for (String cityname : cityList) {
            //根据城市，查询出每个城市每个月份的发车次数
            DepartExportEntity exportDepart = departService.exportDepart(cityname);

            countList.add(exportDepart);
        }

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("解放车发车明细");

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
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\解放车发车次数.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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


        return "1";
    }


    /**
     * 过路费
     *
     * @return
     */
    @GetMapping("/exportIncomeTolls")
    public String exportIncomeTolls() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

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

        HSSFSheet sheet = wb.createSheet("解放车过路费");

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
            fileOutputStream = new FileOutputStream("E:\\解放车过路费.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }


    /**
     * 燃油费
     *
     * @return
     */
    @GetMapping("/exportIncomeFuel")
    public String exportIncomeFuel() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

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

        HSSFSheet sheet = wb.createSheet("解放车燃油费");

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
            fileOutputStream = new FileOutputStream("E:\\解放车燃油费.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }


    /**
     * 罚款
     *
     * @return
     */
    @GetMapping("/exportIncomeFine")
    public String exportIncomeFine() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

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

        HSSFSheet sheet = wb.createSheet("解放车罚款");

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
            fileOutputStream = new FileOutputStream("E:\\解放车罚款.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }


    /**
     * 停车费
     *
     * @return
     */
    @GetMapping("/exportIncomeParking")
    public String exportIncomeParking(String type) {
        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = type;

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

        HSSFSheet sheet = wb.createSheet("解放车停车费");

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
            fileOutputStream = new FileOutputStream("E:\\解放车停车费.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return "success";
    }


    /**
     * 轮胎费
     *
     * @return
     */
    @GetMapping("/exportIncomeTire")
    public String exportIncomeTire() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

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

        HSSFSheet sheet = wb.createSheet("解放车轮胎费");

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
            fileOutputStream = new FileOutputStream("E:\\解放车轮胎费.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }


    /**
     * 维修费
     *
     * @return
     */
    @GetMapping("/exportIncomeRepair")
    public String exportIncomeRepair() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

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

        HSSFSheet sheet = wb.createSheet("解放车维修费");

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
            fileOutputStream = new FileOutputStream("E:\\解放车维修费.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }


    /**
     * 毛利
     *
     * @return
     */
    @GetMapping("/exportIncomeGross")
    public String exportIncomeGross() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        //毛利表中 车队收入列
        Incomestatement totalincomeMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals(ConstantUtil.title1)).collect(Collectors.toList()).get(0);


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

        Incomestatement otherMoney = incomeStatementService.queryMonthMoneyOther(cartype, otherRowTitleList);

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

        Incomestatement totalcostmoney = incomeStatementService.queryMonthMoneyOther(cartype, otherRowTitleList1);

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




        LinkedList<String> titleList = ConstantUtil.makeGrossTitle();


        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("解放车毛利");

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            //表头
            row.createCell((short) i).setCellValue(titleList.get(i));
        }

        for (int i = 0; i < exportEntityList.size(); i++) {
            row = sheet.createRow(i + 1);
            IncomeExportEntity exportEntity = exportEntityList.get(i);

            String[] countListArray = {exportEntity.getMonth(), exportEntity.getRuncount().toString(), exportEntity.getKilosum(),
                    exportEntity.getTotalincomemoney().toString(), exportEntity.getTollsmoney().toString(), exportEntity.getFuelmoney().toString(), exportEntity.getFinesmoney().toString(),
                    exportEntity.getParkingmoney().toString(), exportEntity.getTiremoney().toString(), exportEntity.getRepairmoney().toString(), exportEntity.getOthermoney().toString(), exportEntity.getTotalcostmoney().toString()};

            for (int j = 0; j < countListArray.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell((short) j).setCellValue(countListArray[j]);
            }
        }

        //生成excel
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("E:\\解放车毛利.xls");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return "success";
    }
}
