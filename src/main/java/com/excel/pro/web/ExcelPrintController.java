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
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private DepartDetailDao departDetailDao;

    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private DepartService departService;

    //http://127.0.0.1:9080/hello RestController
    @GetMapping("/hello")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }

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


    @GetMapping("/exportIncome")
    public String exportIncome() {

        List<IncomeExportEntity> exportEntityList = incomeStatementService.exportIncome();

        String cartype = "解放车";

        List<Incomestatement> monthMoneyList = incomeStatementService.queryMonthMoney(cartype);

        Incomestatement monthMoney = monthMoneyList.stream().filter(x -> x.getColumnname().equals("         路桥费")).collect(Collectors.toList()).get(0);

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

   /*     LinkedList<String> titleList = new LinkedList<>();
        titleList.add(ConstantUtil.month);
        titleList.add(ConstantUtil.runcount);
        titleList.add(ConstantUtil.sumkilo);
        titleList.add(ConstantUtil.avg);
        titleList.add(ConstantUtil.money);*/
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


}
