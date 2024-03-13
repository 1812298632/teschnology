package com.excel.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.excel.pro.dao.CarcountDao;
import com.excel.pro.dao.MainlineDao;
import com.excel.pro.dao.NewcarDao;
import com.excel.pro.dao.SystemSetDao;
import com.excel.pro.entity.Carcount;
import com.excel.pro.entity.Mainline;
import com.excel.pro.entity.Newcar;
import com.excel.pro.entity.Systemset;
import com.excel.pro.service.UploadService;
import com.excel.pro.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Resource
    private NewcarDao newcarDao;
    @Resource
    private MainlineDao mainlineDao;
    @Resource
    private CarcountDao carcountDao;
    @Resource
    private SystemSetDao systemSetDao;

    @Autowired
    private  ApplicationContext applicationContext;



    @Override
    public void uploadNewCar(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        ArrayList<String> columnNameList = ConstantUtil.makeNewcar();

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
        LinkedList<Newcar> newcarList = new java.util.LinkedList<>();
        int i = 1;

        for (Row row : sheet) {
            if (row.getRowNum() >= 1) {

                Newcar newcar = new Newcar();

                String network = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));

                newcar.setNetwork(network);

                String carnum = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())));

                newcar.setCarnum(carnum);

                String startdate = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())));

                newcar.setStartdate(startdate);

                String wangdian = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())));

                newcar.setWangdian(wangdian);

                String weight = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString())));

                newcar.setWeight(Double.parseDouble(weight));

                String volume = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString())));

                newcar.setVolume(Double.parseDouble(volume));

                String money = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString())));

                String newMon = money.replace("￥", "");
                String replace = newMon.replace(",", "");

                newcar.setMoney(Double.parseDouble(replace));

                String cost = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));

                newcar.setCost(Double.parseDouble(cost));


                String remark = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(8)).toString())));

                newcar.setRemark(remark);


                newcar.setYear(2023L);
                newcarList.add(newcar);

                log.info("正在插入数据第" + i + "条----" + newcar);

                newcarDao.insert(newcar);
                log.info("插入成功");

                i++;

            }

        }


    }

    @Override
    public void uploadMainline(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        ArrayList<String> columnNameList = ConstantUtil.makeMainline2022();



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
        LinkedList<Mainline> newcarList = new java.util.LinkedList<>();
        int i = 1;

        for (Row row : sheet) {
            //获取从第四行开始的数据，第四行之前都是标题
            if (row.getRowNum() >= 3) {

                Mainline mainline = new Mainline();


                Cell cell2 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString()));
                double num = 0.0;

                if(cell2 == null){
                    continue;
                }
                num = cell2.getNumericCellValue();
                mainline.setNum(num);

                String formno = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())));

                if (formno.equals("") ) {
                    continue;
                }
                mainline.setFormno(formno);


                String carnum = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString())));

                mainline.setCarnum(carnum);

              /*  ArrayList<String> jf = new ArrayList<>();
                jf.add("苏BX6609");
                jf.add("苏BX6615");
                jf.add("苏BX6630");
                jf.add("苏BX6667");
                jf.add("苏BX6679");
                jf.add("苏BX6683");
                jf.add("苏BX6697");

                if (!jf.contains(carnum)) {
                    continue;
                }
*/

                String platenum = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(3)).toString())));

                mainline.setPlatenum(platenum);


                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(4)).toString()));


                double numericCellValue = cell.getNumericCellValue();

                mainline.setFuelmoney(numericCellValue);


                String tollmoney = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(5)).toString())));

                if (!tollmoney.isEmpty()) {
                    mainline.setTollmoney(Double.parseDouble(tollmoney));

                }


                Cell cell1 = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(6)).toString()));

                double numericCellValue1 = cell1.getNumericCellValue();

                mainline.setIncomemoney(numericCellValue1);


                /*String finemoney1 = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));

                if (!finemoney1.isEmpty()) {
                    mainline.setFinemoney1(Double.parseDouble(finemoney1));

                }


                String finemoney2 = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(8)).toString())));

                if (!finemoney2.isEmpty()) {
                    mainline.setFinemoney2(Double.parseDouble(finemoney2));

                }


                String finemoney3 = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(9)).toString())));

                if (!finemoney3.isEmpty()) {
                    mainline.setFinemoney3(Double.parseDouble(finemoney3));

                }


                String finemoney4 = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(10)).toString())));

                if (!finemoney4.isEmpty()) {
                    mainline.setFinemoney4(Double.parseDouble(finemoney4));

                }*/

                String startcity = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(7)).toString())));
                if (!startcity.isEmpty()) {
                    mainline.setStartcity(startcity);

                }

                mainline.setYear(2022L);
                mainline.setMonth(8L);


                log.info("正在插入数据第" + i + "条----" + mainline);

                mainlineDao.insert(mainline);
                log.info("插入成功");

                i++;


            }
        }
    }

    @Override
    public void uploadCarCount(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();
        ArrayList<String> columnNameList = ConstantUtil.makeCarCount();



        //获取需要插入表中的数据列的下标
        for (Row row : sheet) {
            if (row.getRowNum() < 2) {
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
        LinkedList<Carcount> newcarList = new java.util.LinkedList<>();
        int i = 1;

        for (Row row : sheet) {
            //获取从第2行开始的数据，第四行之前都是标题
            if (row.getRowNum() >= 1) {

                Carcount carcount = new Carcount();

                String network = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(0)).toString())));

                if(network.equals("")){
                    continue;
                }
                carcount.setNetwork(network);
                String num = formatter.formatCellValue(row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(1)).toString())));

                carcount.setNum(num);

                Cell cell = row.getCell(Integer.parseInt(titleColumnMap.get(columnNameList.get(2)).toString()));
                //获取excel中的实际值而不是公式
                String len = cell.getStringCellValue();

                carcount.setLength(len);
                carcount.setYear(2022);
                carcount.setMonth(8);



                log.info("正在插入数据第" + i + "条----" + carcount);

                carcountDao.insert(carcount);
                log.info("插入成功");

                i++;
            }
        }

    }



    public void uploadSystemSet(XSSFSheet sheet) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        DataFormatter formatter = new DataFormatter();
        HashMap<String, Object> titleColumnMap = new HashMap<>();


        LambdaQueryWrapper<Systemset> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Systemset::getParentid,1);
        List<Systemset> systemsets = systemSetDao.selectList(wrapper);

        List<String> columnNameList = systemsets.stream().sorted(Comparator.comparing(Systemset::getNum)).map(Systemset::getTitle).collect(Collectors.toList());

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

        System.out.println(titleColumnMap);


        Systemset systemset = systemSetDao.selectById(1);


        Class<?> aClass = Class.forName(systemset.getClassname());

        Constructor<?> constructor = aClass.getConstructor();

        Object o = constructor.newInstance();

        Method method = aClass.getMethod("setFormno", String.class);

        method.invoke(o,"111");


        Object stst = applicationContext.getBean("TestDao");


        Method insert = stst.getClass().getDeclaredMethod("insert", Object.class);

        insert.invoke(stst,o);

        System.out.println(111);



        System.out.println(aClass);
    }
}
