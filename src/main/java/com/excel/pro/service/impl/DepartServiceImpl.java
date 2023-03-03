package com.excel.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.dao.DepartOtherDao;
import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.Departother;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.service.DepartService;
import com.excel.pro.util.ConstantUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class DepartServiceImpl implements DepartService {
    Logger logger = LoggerFactory.getLogger(DepartServiceImpl.class);

    @Resource
    private DepartDetailDao departDetailDao;
    @Resource
    private DepartOtherDao departOtherDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDepartDetail(String sheetName) throws FileNotFoundException {
        // TODO: 2022/5/22 需要获取的excel路径
        File file = new File("C:\\Users\\18122\\Desktop\\快捷方式\\excel\\解放车过路费.XLSX");
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

        //需要保存哪些列的数据
       /* ArrayList<String> columnNameList = new ArrayList<>();
        columnNameList.add(ConstantUtil.carnum);
        columnNameList.add(ConstantUtil.startcity);
        columnNameList.add(ConstantUtil.endcity);
        columnNameList.add(ConstantUtil.startkilo);
        columnNameList.add(ConstantUtil.endkilo);
        columnNameList.add(ConstantUtil.kilo);
        columnNameList.add(ConstantUtil.id);
        columnNameList.add(ConstantUtil.number);*/

        ArrayList<String> columnNameList = ConstantUtil.makeDepartColumnNameList();

        LinkedList<Departdetail> departDetailList = new LinkedList<>();

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
            }
        }
        for (Row row : sheet) {
            //获取从第四行开始的数据，第四行之前都是标题
            if (row.getRowNum() >= 3) {
                String s = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));

                //如果获取到的派单车号数据为空，结束本次循环
                if (s.equals("")) {
                    continue;
                }
                //派车单号 数据
                String carnum = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));

                String startcity = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())));
                String endctiy = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())));

                if (carnum.equals("")) {
                    carnum = "ooooooooo";
                }
                if (startcity.equals("")) {
                    continue;
                }
                if (endctiy.equals("")) {
                    continue;
                }


                Cell cell1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString()));

                double id = cell1.getNumericCellValue();

                Departdetail departdetail = new Departdetail();

                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString()));
                //获取excel中的实际值而不是公式
                double numericCellValue = cell.getNumericCellValue();


                departdetail.setCarnum(carnum);
                departdetail.setStartcity(startcity);
                departdetail.setEndcity(endctiy);

                departdetail.setStartkilo(Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())))));
                departdetail.setEndkilo(Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString())))));
                departdetail.setKilo((long) numericCellValue);
                departdetail.setSheet(sheetName);
                departdetail.setSheetid((long) id);

                logger.info("正在插入" + sheetName + "序列号为" + (long) id + "的数据");
                departDetailDao.insert(departdetail);
                logger.info(sheetName + "序列号为" + (long) id + "的数据插入成功");

                departDetailList.add(departdetail);
            }
        }

    }

    @Override
    public List<Departdetail> querybyid() {
        return departDetailDao.queryByid();
    }

    @Override
    public DepartExportEntity exportDepart(String cityname, String cartype,String year) {
        return departDetailDao.exportDepart(cityname, cartype,year);
    }

    @Override
    public void insertDepartDeatilByUpload(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        ArrayList<String> columnNameList = new ArrayList<>();
        String month111 = ConstantUtil.departUploadEntity.getMonth();
        if (ConstantUtil.departUploadEntity.getYear().equals("2022") && (month111.equals("6") || month111.equals("7") || month111.equals("8"))) {
            columnNameList = ConstantUtil.makeDepartColumnNameList1();
        } else {
            columnNameList = ConstantUtil.makeDepartColumnNameList();
        }


        //获取需要插入表中的数据列的下标
        for (Row row : sheet) {
            if (row.getRowNum() < 4) {
                //for循环row中的所有sheet
                for (Cell cell : row) {
                    for (String s : columnNameList) {
                        if (formatter.formatCellValue(cell).equals(s)) {//将获取到的cell数据格式化，不然会出错
                            //如果进入了if判断，说明找到了数据，将列的下标存入map中
                            titleColumnMap.put(s, cell.getColumnIndex());
                        }
                    }
                }
            }
        }
        LinkedList<Departdetail> departDetailList = new LinkedList<>();
        String sheetName = ConstantUtil.departUploadEntity.getSheetname();

        for (Row row : sheet) {
            //获取从第四行开始的数据，第四行之前都是标题
            if (row.getRowNum() >= 3) {
                //表单号
                String fromno = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));
                //派车单号 数据
                String carnum = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));

                //如果获取到的派单车 和 表单好 号数据都为空，结束本次循环
                if (fromno.equals("") && carnum.equals("")) {
                    continue;
                }

                String headPlate = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(8)).toString())));

                ArrayList<String> wew = new ArrayList<>();
                wew.add("苏BA7657");
                wew.add("苏BA7673");
                wew.add("苏BB0057");
                wew.add("苏BB0065");
                wew.add("苏BB0066");
                ArrayList<String> jf = new ArrayList<>();
                jf.add("苏BX6609");
                jf.add("苏BX6615");
                jf.add("苏BX6630");
                jf.add("苏BX6667");
                jf.add("苏BX6679");
                jf.add("苏BX6683");
                jf.add("苏BX6697");

                boolean isErr = true;

                if (ConstantUtil.departUploadEntity.getCartype().equals("沃尔沃")) {

                    for (String s : wew) {
                        if (s.equals(headPlate)) {
                            isErr = false;
                        }
                    }

                } else if (ConstantUtil.departUploadEntity.getCartype().equals("解放车")) {
                    for (String s : jf) {
                        if (s.equals(headPlate)) {
                            isErr = false;
                        }
                    }

                }

                //车牌号不对应，结束循环
                if (isErr) {
                    continue;
                }


                Departdetail departdetail = new Departdetail();

                //起点
                String startcity = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())));
                //终点
                String endctiy = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())));

                //sheet中的序列
                Cell cell1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString()));
                double sheetid = cell1.getNumericCellValue();

                //行驶里程
                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString()));
                //获取excel中的实际值而不是公式
                double numericCellValue = cell.getNumericCellValue();

                Long starkilo = Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString()))));
                Long endkilo = Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString()))));
                String fronno = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));

                departdetail.setCarnum(carnum);
                departdetail.setStartcity(startcity);
                departdetail.setEndcity(endctiy);
                departdetail.setStartkilo(starkilo);
                departdetail.setEndkilo(endkilo);
                departdetail.setKilo((long) numericCellValue);
                departdetail.setSheet(sheetName);
                departdetail.setSheetid((long) sheetid);
                departdetail.setFromno(fronno);
                departdetail.setMonth(Long.parseLong(ConstantUtil.departUploadEntity.getMonth()));
                departdetail.setCartype(ConstantUtil.departUploadEntity.getCartype());
                departdetail.setExcelname(ConstantUtil.departUploadEntity.getExcelname());
                departdetail.setYear(Long.parseLong(ConstantUtil.departUploadEntity.getYear()));
                departdetail.setHeadplate(headPlate);

                logger.info("正在插入" + sheetName + "序列号为" + (long) sheetid + "的数据");
                departDetailDao.insert(departdetail);
                logger.info(sheetName + "序列号为" + (long) sheetid + "的数据插入成功");

                departDetailList.add(departdetail);
            }
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Departdetail departdetail, UpdateWrapper<Departdetail> updateWrapper, ResponseEntity responseEntity) {
        int update = departDetailDao.update(departdetail, updateWrapper);
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
    public void delete(LambdaQueryWrapper<Departdetail> queryWrapper, ResponseEntity responseEntity) {
        int delete = departDetailDao.delete(queryWrapper);
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
    public void insertDepartother(String sheetname, int month) throws FileNotFoundException {

        // TODO: 2022/5/22 需要获取的excel路径
        File file = new File("C:\\Users\\18122\\Desktop\\abb2022一季度.XLSX");
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
        XSSFSheet sheet = excel.getSheet(sheetname);

        //需要保存哪些列的数据
       /* ArrayList<String> columnNameList = new ArrayList<>();
        columnNameList.add(ConstantUtil.carnum);
        columnNameList.add(ConstantUtil.startcity);
        columnNameList.add(ConstantUtil.endcity);
        columnNameList.add(ConstantUtil.startkilo);
        columnNameList.add(ConstantUtil.endkilo);
        columnNameList.add(ConstantUtil.kilo);
        columnNameList.add(ConstantUtil.id);
        columnNameList.add(ConstantUtil.number);*/

        ArrayList<String> columnNameList = ConstantUtil.makeDepartOtherList();

        LinkedList<Departdetail> departDetailList = new LinkedList<>();

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
            }
        }
        for (Row row : sheet) {
            //获取从第四行开始的数据，第四行之前都是标题
            if (row.getRowNum() >= 4) {
                //String id = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));

                Cell cell3 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString()));
                if (cell3 == null) {
                    continue;
                }
                cell3.setCellType(CellType.STRING);

                String id = cell3.getStringCellValue();


                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString()));
                cell.setCellType(CellType.STRING);

                //获取excel中的实际值而不是公式
                String numericCellValue = cell.getStringCellValue();


                //如果获取到的派单车号数据为空，结束本次循环
                if (id.equals("") && numericCellValue.equals("")) {
                    continue;
                }


                Departother departother = new Departother();

                departother.setId(id);


                Cell cell1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString()));
                cell1.setCellType(CellType.STRING);
                //获取excel中的实际值而不是公式
                String type = cell1.getStringCellValue();
                departother.setType(type);


                Cell cell2 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString()));
                cell2.setCellType(CellType.STRING);
                departother.setCount(cell2.getStringCellValue());

                departother.setMonth(Integer.valueOf(month).toString());
                departother.setMoney(numericCellValue);

                logger.info("正在插入" + sheetname + "序列号为" + id + "的数据");
                departOtherDao.insert(departother);
                logger.info(sheetname + "序列号为" + id + "的数据插入成功");

            }
        }
    }
}
