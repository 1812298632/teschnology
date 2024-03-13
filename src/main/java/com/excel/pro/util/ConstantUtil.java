package com.excel.pro.util;

import com.excel.pro.entity.DepartUploadEntity;
import com.excel.pro.entity.IncomeUploadEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;

public class ConstantUtil {


    //要导入哪些sheet的数据
    public static final String abb01 = "序号";
    public static final String abb02 = "派车类型";
    public static final String abb03 = "票数";
    public static final String abb04 = "核算收入";


    public static final String s99701 = "序号";
    public static final String s99702 = "派车类型";
    public static final String s99703 = "货物名称";
    public static final String s99704 = "核算收入";

    public static ArrayList<String> makeDepartOtherList() {
        ArrayList<String> departOtherColumnNameList = new ArrayList<>();

        departOtherColumnNameList.add(ConstantUtil.abb01);
        departOtherColumnNameList.add(ConstantUtil.abb02);
        departOtherColumnNameList.add(ConstantUtil.abb03);
        departOtherColumnNameList.add(ConstantUtil.abb04);


      /*  departOtherColumnNameList.add(ConstantUtil.s99701);
        departOtherColumnNameList.add(ConstantUtil.s99702);
        departOtherColumnNameList.add(ConstantUtil.s99703);
        departOtherColumnNameList.add(ConstantUtil.s99704);
*/

        return departOtherColumnNameList;
    }


    //要导入哪些sheet的数据
    public static final String sheetName1 = "解放车1";
    public static final String sheetName2 = "解放车2";
    public static final String sheetName3 = "解放车3";
    public static final String sheetName4 = "解放车4";
    public static final String sheetName5 = "解放车5";
    public static final String sheetName6 = "解放车6";
    public static final String sheetName7 = "解放车7";
    public static final String sheetName8 = "解放车8";
    public static final String sheetName9 = "解放车9";
    public static final String sheetName10 = "解放车10";
    public static final String sheetName11 = "解放车11";
    public static final String sheetName12 = "解放车12";

    public static LinkedList<String> makeSheetNameList() {
        LinkedList<String> sheetNameList = new LinkedList();
        sheetNameList.add(sheetName3);
        sheetNameList.add(sheetName4);
        sheetNameList.add(sheetName5);
        sheetNameList.add(sheetName6);
        sheetNameList.add(sheetName7);
        sheetNameList.add(sheetName8);
        sheetNameList.add(sheetName9);
        sheetNameList.add(sheetName10);
        sheetNameList.add(sheetName11);
        sheetNameList.add(sheetName12);
        sheetNameList.add(sheetName1);
        sheetNameList.add(sheetName2);

        return sheetNameList;
    }


    public static LinkedList<String> makeSheetNameOthrList() {
        LinkedList<String> sheetNameList = new LinkedList();
        /*sheetNameList.add("辽ABB872收入1");
        sheetNameList.add("辽ABB872收入2");*/
        sheetNameList.add("辽ABB872收入3");
        sheetNameList.add("辽ABB872收入4");
        sheetNameList.add("辽ABB872收入5");
       /* sheetNameList.add("辽ABB872收入6");
        sheetNameList.add("辽ABB872收入7");
        sheetNameList.add("辽ABB872收入8");
        sheetNameList.add("辽ABB872收入9");
        sheetNameList.add("辽ABB872收入10");
        sheetNameList.add("辽ABB872收入11");
        sheetNameList.add("辽ABB872收入12");
        */
        /*sheetNameList.add("短途车辆收入统计表3");
        sheetNameList.add("短途车辆收入统计表4");
        sheetNameList.add("短途车辆收入统计表5");
        sheetNameList.add("短途车辆收入统计表6");
        sheetNameList.add("短途车辆收入统计表7");
        sheetNameList.add("短途车辆收入统计表8");
        sheetNameList.add("短途车辆收入统计表9");
        sheetNameList.add("短途车辆收入统计表10");
        sheetNameList.add("短途车辆收入统计表11");
        sheetNameList.add("短途车辆收入统计表12");*/
        return sheetNameList;
    }


    //导入发车明细中的哪些列title数据  作业台帐
    public static final String carnum = "派车单号";
    public static final String startcity = "起点";
    public static final String endcity = "终点";
    public static final String startkilo = "起始公里数";
    public static final String endkilo = "结束公里数";
    public static final String kilo = "行驶公里数";
    public static final String id = "序列号";
    public static final String fromno = "表单号";

    public static final String carnum1 = "发车单号";

    public static final String headplate = "车头牌号";

    public static final String data = "日期";
    public static final String type = "怕日车";


    public static ArrayList<String> makeDepartColumnNameList() {
        ArrayList<String> departColumnNameList = new ArrayList<>();

        departColumnNameList.add(ConstantUtil.carnum);
        departColumnNameList.add(ConstantUtil.startcity);
        departColumnNameList.add(ConstantUtil.endcity);
        departColumnNameList.add(ConstantUtil.startkilo);
        departColumnNameList.add(ConstantUtil.endkilo);
        departColumnNameList.add(ConstantUtil.kilo);
        departColumnNameList.add(ConstantUtil.id);
        departColumnNameList.add(ConstantUtil.fromno);
        departColumnNameList.add(ConstantUtil.headplate);

        return departColumnNameList;
    }

    public static ArrayList<String> makeDepartColumnNameList1() {
        ArrayList<String> departColumnNameList = new ArrayList<>();

        departColumnNameList.add(ConstantUtil.carnum1);
        departColumnNameList.add(ConstantUtil.startcity);
        departColumnNameList.add(ConstantUtil.endcity);
        departColumnNameList.add(ConstantUtil.startkilo);
        departColumnNameList.add(ConstantUtil.endkilo);
        departColumnNameList.add(ConstantUtil.kilo);
        departColumnNameList.add(ConstantUtil.id);
        departColumnNameList.add(ConstantUtil.fromno);
        departColumnNameList.add(ConstantUtil.headplate);

        return departColumnNameList;
    }

    public static final String network1 = "发车网点";
    public static final String carnumnew1 = "车牌号码";
    public static final String startdate1 = "发车日期（分析时间）";
    public static final String wangdian1 = "分流网点";
    public static final String weight1 = "装车重量";
    public static final String volume1 = "装车体积";
    public static final String money1 = "司机总运费";
    public static final String cost1 = "大车提送成本";
    public static final String remark1 = "备注";

    public static ArrayList<String> makeNewcar() {

        ArrayList<String> list = new ArrayList<>();
        list.add(network1);
        list.add(carnumnew1);
        list.add(startdate1);
        list.add(wangdian1);
        list.add(weight1);
        list.add(volume1);
        list.add(money1);
        list.add(cost1);
        list.add(remark1);

        return list;
    }

    public static final String num2 = "序列号";
    public static final String formno2 = "表单号";
    public static final String carnum2 = "车头牌号";
    public static final String platenum2 = "挂车牌号";
    public static final String fuelmoney2 = "金额合计";
    public static final String tollmoney2 = "过路费(元）";
    public static final String incomemoney2 = "干线运费（元）";
    public static final String finemoney1 = "牵引车无票罚款（元）";
    public static final String finemoney2 = "牵引车有票罚款（元）";
    public static final String finemoney3 = "挂车无票罚款（元）";
    public static final String finemoney4 = "挂车有票罚款（元）";
    public static final String startcity1 = "起点";


    public static ArrayList<String> makeMainline2023() {

        ArrayList<String> list = new ArrayList<>();
        list.add(num2);
        list.add(formno2);
        list.add(carnum2);
        list.add(platenum2);
        list.add(fuelmoney2);
        list.add(tollmoney2);
        list.add(incomemoney2);
        list.add(finemoney1);
        list.add(finemoney2);
        list.add(finemoney3);
        list.add(finemoney4);
        list.add(startcity1);

        return list;
    }


    public static final String num3 = "序列号";
    public static final String formno3 = "表单号";
    public static final String carnum3 = "车头牌号";
    public static final String platenum3 = "挂车牌号";
    //public static final String fuelmoney3 = "油费合计金额";解放
    public static final String fuelmoney3 = "金额合计";//沃尔沃
    //public static final String tollmoney3 = "过路费"; 解放
    public static final String tollmoney3 = "过路费(元）";//沃尔沃
    public static final String incomemoney3 = "干线运费（元）";

    public static final String startcity13 = "起点";

    //长春 沈阳 无锡 无锡二部 油费 过路费 起点表头
    public static ArrayList<String> makeMainline2022() {

        ArrayList<String> list = new ArrayList<>();
        list.add(num3);
        list.add(formno3);
        list.add(carnum3);
        list.add(platenum3);
        list.add(fuelmoney3);
        list.add(tollmoney3);
        list.add(incomemoney3);

        list.add(startcity13);

        return list;
    }


    public static final String network3 = "发车网点";
    public static final String num5  = "发车单号";
    public static final String length  = "车长";

    //发车次数占比表头
    public static ArrayList<String> makeCarCount() {

        ArrayList<String> list = new ArrayList<>();
        list.add(network3);
        list.add(num5);
        list.add(length);

        return list;
    }


    public static ArrayList<String> makeDepartColumnNameList98997() {
        ArrayList<String> departColumnNameList = new ArrayList<>();

        departColumnNameList.add(ConstantUtil.carnum);
        departColumnNameList.add(ConstantUtil.startcity);
        departColumnNameList.add(ConstantUtil.endcity);
        departColumnNameList.add(ConstantUtil.startkilo);
        departColumnNameList.add(ConstantUtil.endkilo);
        departColumnNameList.add(ConstantUtil.kilo);
        departColumnNameList.add(ConstantUtil.id);
        departColumnNameList.add(ConstantUtil.fromno);

        return departColumnNameList;
    }


    //导出 过路费 燃油费 罚款等，需要的表头名称
    public static final String month = "月份";
    public static final String runcount = "月度运行单程数";
    public static final String sumkilo = "总里程";
    public static final String avg = "平均里程";
    public static final String money = "月度总费用";
    public static final String avgonway = "平均单程费用";
    public static final String cost = "每公里成本";

    public static LinkedList makeToolsTitle() {
        LinkedList<String> toolsTitleList = new LinkedList();
        toolsTitleList.add(month);
        toolsTitleList.add(runcount);
        toolsTitleList.add(sumkilo);
        toolsTitleList.add(avg);
        toolsTitleList.add(money);
        toolsTitleList.add(avgonway);
        toolsTitleList.add(cost);

        return toolsTitleList;
    }

    public static final String fuelingLiters = "加油升数";
    public static final String fuelSavingLiters = "节油升数";
    public static final String hundredFule = "百公里油耗";
    public static final String monthAvg = "月平均单价";
    public static final String fuelSavingMoney = "节油金额";


    public static LinkedList makeYhTitle() {
        LinkedList<String> titleList = new LinkedList();
        titleList.add(month);
        titleList.add(fuelingLiters);
        titleList.add(sumkilo);
        titleList.add(hundredFule);
        titleList.add(fuelSavingLiters);
        titleList.add(monthAvg);
        titleList.add(fuelSavingMoney);


        return titleList;
    }


    //导出 毛利 表头名称
    public static final String totalincome = "总收入";
    public static final String kilomoney = "每公里收入";
    public static final String tolls = "过路费";
    public static final String fuel = "燃油费";
    public static final String fines = "罚款";
    public static final String parking = "停车费";
    public static final String tire = "轮胎费";
    public static final String repair = "维修费";
    public static final String man = "人工成本";
    public static final String other = "其他";
    public static final String totalcost = "总成本";
    public static final String kilocost = "公里成本费用";
    public static final String gross = "毛利";
    public static final String grossrate = "毛利率";
    public static final String grossonway = "单程毛利";


    public static LinkedList makeGrossTitle() {
        LinkedList<String> titleList = new LinkedList();
        titleList.add(month);
        titleList.add(runcount);
        titleList.add(sumkilo);
        titleList.add(totalincome);
        titleList.add(kilomoney);
        titleList.add(tolls);
        titleList.add(fuel);
        titleList.add(fines);
        titleList.add(parking);
        titleList.add(tire);
        titleList.add(repair);
        titleList.add(man);
        titleList.add(other);
        titleList.add(totalcost);
        titleList.add(kilocost);
        titleList.add(gross);
        titleList.add(grossrate);
        titleList.add(grossonway);

        return titleList;
    }


    //导出发车明细,需要的表头名称
    public static final String department = "部门";
    public static final String threemonth = "3月";
    public static final String fourmonth = "4月";
    public static final String fivemonth = "5月";
    public static final String sixmonth = "6月";
    public static final String sevenmonth = "7月";
    public static final String eightmonth = "8月";
    public static final String ninemonth = "9月";
    public static final String tenmonth = "10月";
    public static final String elevenmonth = "11月";
    public static final String twelvemonth = "12月";
    public static final String onemonth = "1月";
    public static final String twomonth = "2月";
    public static final String sumcount = "合计发车次数";

    public static LinkedList makeDepartTitle() {
        LinkedList<String> departTitleList = new LinkedList();
        departTitleList.add(department);
        departTitleList.add(threemonth);
        departTitleList.add(fourmonth);
        departTitleList.add(fivemonth);
        departTitleList.add(sixmonth);
        departTitleList.add(sevenmonth);
        departTitleList.add(eightmonth);
        departTitleList.add(ninemonth);
        departTitleList.add(tenmonth);
        departTitleList.add(elevenmonth);
        departTitleList.add(twelvemonth);
        departTitleList.add(onemonth);
        departTitleList.add(twomonth);
        departTitleList.add(sumcount);

        return departTitleList;
    }

    //导出发车次数中 所需要每行title的城市
    public static final String wuxi = "无锡";
    public static final String wuxierbu = "无锡二部";
    public static final String changchun = "长春";
    public static final String suzhou = "苏州";
    public static final String shanghai = "上海";
    public static final String dadong = "大东";
    public static final String changzhou = "常州";
    public static final String shenyang = "沈阳";
    public static final String kunshan = "昆山";
    public static final String haerbin = "哈尔滨";

    public static LinkedList<String> makeCityNameRowTitle() {
        LinkedList<String> cityNameRowTitleList = new LinkedList<>();

        cityNameRowTitleList.add(wuxi);
        cityNameRowTitleList.add(wuxierbu);
        cityNameRowTitleList.add(changchun);
        cityNameRowTitleList.add(suzhou);
        cityNameRowTitleList.add(shanghai);
        cityNameRowTitleList.add(dadong);
        cityNameRowTitleList.add(changzhou);
        cityNameRowTitleList.add(shenyang);
        cityNameRowTitleList.add(kunshan);
        cityNameRowTitleList.add(haerbin);
        return cityNameRowTitleList;
    }


    //需要保存损益表中哪些列的数据
    public static final String subjectcode = "科目编码";
    public static final String nowmonth = "本年累计";

    public static ArrayList<String> makeIncomeImportTitle() {
        ArrayList<String> incomeImportList = new ArrayList<>();

        incomeImportList.add(ConstantUtil.subjectcode);
        incomeImportList.add(ConstantUtil.onemonth);
        incomeImportList.add(ConstantUtil.twomonth);
        incomeImportList.add(ConstantUtil.threemonth);
        incomeImportList.add(ConstantUtil.fourmonth);
        incomeImportList.add(ConstantUtil.fivemonth);
        incomeImportList.add(ConstantUtil.sixmonth);
        incomeImportList.add(ConstantUtil.sevenmonth);
        incomeImportList.add(ConstantUtil.eightmonth);
        incomeImportList.add(ConstantUtil.ninemonth);
        incomeImportList.add(ConstantUtil.tenmonth);
        incomeImportList.add(ConstantUtil.elevenmonth);
        incomeImportList.add(ConstantUtil.twelvemonth);
        return incomeImportList;
    }

    public static ArrayList<String> makeIncomeImportTitle2023() {
        ArrayList<String> incomeImportList = new ArrayList<>();

        incomeImportList.add(ConstantUtil.nowmonth);
        incomeImportList.add(ConstantUtil.onemonth);
        incomeImportList.add(ConstantUtil.twomonth);
        incomeImportList.add(ConstantUtil.threemonth);
        incomeImportList.add(ConstantUtil.fourmonth);
        incomeImportList.add(ConstantUtil.fivemonth);
        incomeImportList.add(ConstantUtil.sixmonth);
        incomeImportList.add(ConstantUtil.sevenmonth);
        incomeImportList.add(ConstantUtil.eightmonth);
        incomeImportList.add(ConstantUtil.ninemonth);
        incomeImportList.add(ConstantUtil.tenmonth);
        incomeImportList.add(ConstantUtil.elevenmonth);
        incomeImportList.add(ConstantUtil.twelvemonth);
        return incomeImportList;
    }


    //需要保存损益表中哪些表头
    public static final String title1 = "   车队收入";
    public static final String title2 = "       车队营业收人";
    public static final String title3 = "         燃油费";
    public static final String title4 = "         路桥费";
    public static final String title5 = "         停车费";
    public static final String title6 = "         罚金、滞纳金";
    public static final String title7 = "         车辆维修费";
    public static final String title8 = "            日常维修";
    public static final String title9 = "            轮胎维修";
    public static final String title10 = "            大修基金";
    public static final String title11 = "       差旅费";
    public static final String title12 = "       车队其他";
    public static final String title13 = "       伙食费";
    public static final String title14 = "   车队成本";
    public static final String title15 = "         物料消耗";
    public static final String title16 = "一、营业收入总额";
    public static final String title17 = "  （一） 主营业务收入";
    public static final String title18 = "二、营业成本总额";
    public static final String title19 = "   车队成本";
    public static final String title20 = "     2.人工成本";
    public static final String title21 = "七、净利润";
    public static final String title22 = "  1.加油成本率";
    public static final String title23 = "  2.路桥成本率";
    public static final String title24 = "  3.人工成本率";


    public static final String title25 = "主营业务收入";
    public static final String title26 = "            燃油费";
    public static final String title27 = "            通行费";
    public static final String title28 = "            停车费";
    public static final String title29 = "        20.罚款、滞纳金";
    public static final String title30 = "            车辆维修费";
    public static final String title31 = "        24. 其他";
    public static final String title33 = "        11.伙食费";
    public static final String title34 = "    作业成本";
    public static final String title35 = "主营业务收入";
    public static final String title36 = "减：主营业务成本";
    public static final String title37 = "        9.差旅费";
    public static final String title38 = "";

    public static ArrayList<String> makeIncomeImportRowTitle() {
        ArrayList<String> incomeImportRowTitleList = new ArrayList<>();

        incomeImportRowTitleList.add(title1);
        incomeImportRowTitleList.add(title2);
        incomeImportRowTitleList.add(title3);
        incomeImportRowTitleList.add(title4);
        incomeImportRowTitleList.add(title5);
        incomeImportRowTitleList.add(title6);
        incomeImportRowTitleList.add(title7);
        incomeImportRowTitleList.add(title8);
        incomeImportRowTitleList.add(title9);
        incomeImportRowTitleList.add(title10);
        incomeImportRowTitleList.add(title11);
        incomeImportRowTitleList.add(title12);
        incomeImportRowTitleList.add(title13);
        incomeImportRowTitleList.add(title18);
        incomeImportRowTitleList.add(title15);
        incomeImportRowTitleList.add(title19);
        incomeImportRowTitleList.add(title20);
        incomeImportRowTitleList.add(title21);
        //incomeImportRowTitleList.add(title22);
        //incomeImportRowTitleList.add(title23);
        //incomeImportRowTitleList.add(title24);


        return incomeImportRowTitleList;
    }

    public static final String title20222_01 = "（一）营业外收入";
    public static final String title20222_02 = "二、营业成本总额";
    public static final String title20222_03 = "四、  管理费用 ";
    public static final String title20222_04 = "（二）营业外支出";


    public static ArrayList<String> makeIncomeImportRowTitle2022() {
        ArrayList<String> incomeImportRowTitleList = new ArrayList<>();

        incomeImportRowTitleList.add(title16);
        incomeImportRowTitleList.add(title17);
        incomeImportRowTitleList.add(title3);
        incomeImportRowTitleList.add(title4);
        incomeImportRowTitleList.add(title5);
        incomeImportRowTitleList.add(title6);
        incomeImportRowTitleList.add(title7);
        incomeImportRowTitleList.add(title8);
        incomeImportRowTitleList.add(title9);
        incomeImportRowTitleList.add(title10);
        incomeImportRowTitleList.add(title11);
        incomeImportRowTitleList.add(title12);
        incomeImportRowTitleList.add(title13);
        incomeImportRowTitleList.add(title14);
        incomeImportRowTitleList.add(title15);
        incomeImportRowTitleList.add(title20);
        incomeImportRowTitleList.add(title20222_01);
        incomeImportRowTitleList.add(title20222_02);
        incomeImportRowTitleList.add(title20222_03);
        incomeImportRowTitleList.add(title20222_04);
        //营业外收入
        //营业成本总额
        //管理费用
        //营业外支出


        return incomeImportRowTitleList;
    }

    public static final String title23_01 = "减：其他业务支出";
    public static final String title23_02 = "    管理费用";
    public static final String title23_03 = "减：营业外支出";
    public static final String title23_04 = "加：其他业务收入";
    public static final String title23_05 = "加：营业外收入";

    public static ArrayList<String> makeIncomeImportRowTitle2023() {
        ArrayList<String> incomeImportRowTitleList = new ArrayList<>();

        incomeImportRowTitleList.add(title35);//1
        incomeImportRowTitleList.add(title25);//1
        incomeImportRowTitleList.add(title26);//1
        incomeImportRowTitleList.add(title27);//1
        incomeImportRowTitleList.add(title28);//1
        incomeImportRowTitleList.add(title29);//1
        incomeImportRowTitleList.add(title30);//1
        //incomeImportRowTitleList.add(title8);
        //incomeImportRowTitleList.add(title9);//no
        incomeImportRowTitleList.add(title10);//no
        incomeImportRowTitleList.add(title11);//no
        incomeImportRowTitleList.add(title31);//1
        incomeImportRowTitleList.add(title33);//1
        incomeImportRowTitleList.add(title36);//1
        //incomeImportRowTitleList.add(title15);
        incomeImportRowTitleList.add(title37);//1
        incomeImportRowTitleList.add(title34);//1
        incomeImportRowTitleList.add(title23_01);//1（二） 其他业务支出
        incomeImportRowTitleList.add(title23_02);//1 管理费用
        incomeImportRowTitleList.add(title23_04);//1其他业务收入
        incomeImportRowTitleList.add(title23_05);//1 营业外收入



        return incomeImportRowTitleList;
    }


    public static ArrayList<String> makeIncomeImportRowTitle2021() {
        ArrayList<String> incomeImportRowTitleList = new ArrayList<>();

        incomeImportRowTitleList.add(title1);
        incomeImportRowTitleList.add(title2);
        incomeImportRowTitleList.add(title3);
        incomeImportRowTitleList.add(title4);
        incomeImportRowTitleList.add(title5);
        incomeImportRowTitleList.add(title6);
        incomeImportRowTitleList.add(title7);
        incomeImportRowTitleList.add(title8);
        incomeImportRowTitleList.add(title9);
        incomeImportRowTitleList.add(title10);
        incomeImportRowTitleList.add(title11);
        incomeImportRowTitleList.add(title12);
        incomeImportRowTitleList.add(title13);
        incomeImportRowTitleList.add(title14);
        incomeImportRowTitleList.add(title15);


        return incomeImportRowTitleList;
    }


    //损溢表中需要获取哪些sheet的数据 sheetname一般为车牌号
    public static final String carid1 = "苏BB0057";
    public static final String carid2 = "苏BX6615";
    public static final String carid3 = "苏BX6667";
    public static final String carid4 = "苏BX6609";
    public static final String carid5 = "苏B98997";
    public static final String carid6 = "沃尔沃";
    public static final String carid7 = "解放车";

    public static LinkedList<String> makeIncomeSheetNameList() {
        LinkedList<String> incomeSheetNameList = new LinkedList<>();

        incomeSheetNameList.add(carid1);
        incomeSheetNameList.add(carid2);
        incomeSheetNameList.add(carid3);
        incomeSheetNameList.add(carid4);
        incomeSheetNameList.add(carid5);
        incomeSheetNameList.add(carid6);
        incomeSheetNameList.add(carid7);

        return incomeSheetNameList;
    }


    //用于保存文件导入时的基本信息
    public static DepartUploadEntity departUploadEntity = new DepartUploadEntity();
    public static IncomeUploadEntity incomeUploadEntity = new IncomeUploadEntity();

    public static String RESPONSE_SUCCESS = "success";
    public static String RESPONSE_ERROR = "error";
    public static String RESPONSE_WARNING = "warning";


    /**
     * excel表格直接下载
     */
    public static void exportExcelByDownload(HSSFWorkbook wb, HttpServletResponse httpServletResponse, String fileName) throws Exception {
        //响应类型为application/octet- stream情况下使用了这个头信息的话，那就意味着不想直接显示内容
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        //attachment为以附件方式下载
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(
                fileName + ".xlsx",
                "utf-8"));
        /**
         * 代码里面使用Content-Disposition来确保浏览器弹出下载对话框的时候。
         * response.addHeader("Content-Disposition","attachment");一定要确保没有做过关于禁止浏览器缓存的操作
         */
        httpServletResponse.setHeader("Cache-Control", "No-cache");
        httpServletResponse.flushBuffer();
        wb.write(httpServletResponse.getOutputStream());
        wb.close();
    }
}
