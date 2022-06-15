package com.excel.pro.service.impl;

import com.excel.pro.dao.DepartDetailDao;
import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.service.DepartService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class DepartServiceImpl implements DepartService {
    Logger logger = LoggerFactory.getLogger(DepartServiceImpl.class);

    @Resource
    private DepartDetailDao departDetailDao;


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
    public DepartExportEntity exportDepart(String cityname, String cartype) {
        return departDetailDao.exportDepart(cityname,cartype);
    }

    @Override
    public void insertDepartDeatilByUpload(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        ArrayList<String> columnNameList = ConstantUtil.makeDepartColumnNameList();

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

                logger.info("正在插入" + sheetName + "序列号为" + (long) sheetid + "的数据");
                departDetailDao.insert(departdetail);
                logger.info(sheetName + "序列号为" + (long) sheetid + "的数据插入成功");

                departDetailList.add(departdetail);
            }
        }


    }
}
