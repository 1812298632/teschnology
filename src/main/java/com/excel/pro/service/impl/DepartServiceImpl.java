package com.excel.pro.service.impl;

import com.excel.pro.dao.impl.DepartDetailDao;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.service.DepartService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

@Service
public class DepartServiceImpl implements DepartService {

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

        ArrayList<String> columnNameList = new ArrayList<>();
        //需要保存哪些列的数据
        columnNameList.add("派车单号");
        columnNameList.add("起点");
        columnNameList.add("终点");
        columnNameList.add("起始公里数");
        columnNameList.add("结束公里数");
        columnNameList.add("行驶公里数");
        columnNameList.add("序列号");

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
            if(row.getRowNum()>=3){
                String s = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));
                if(s.equals("")){
                    continue;
                }
                Departdetail departdetail = new Departdetail();

                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString()));
                //获取excel中的实际值而不是公式
                double numericCellValue = cell.getNumericCellValue();

                Cell cell1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString()));
                double id = cell1.getNumericCellValue();


                departdetail.setCarnum(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString()))));
                departdetail.setStartcity(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString()))));
                departdetail.setEndcity(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString()))));

                departdetail.setStartkilo(Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())))));
                departdetail.setEndkilo(Long.valueOf(formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString())))));
                departdetail.setKilo((long)numericCellValue);
                departdetail.setSheet(sheetName);
                departdetail.setSheetid((long)id);

                System.out.println("正在插入"+sheetName+"序列号为"+(long)id+"的数据");
                departDetailDao.insert(departdetail);
                System.out.println(sheetName+"序列号为"+(long)id+"的数据插入成功");

                departDetailList.add(departdetail);
            }
        }

    }
}
