package com.excel.pro.util;

import java.util.ArrayList;
import java.util.LinkedList;

public class ConstantUtil {

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


    //导入发车明细中的哪些列title数据  作业台帐
    public static final String carnum = "派车单号";
    public static final String startcity = "起点";
    public static final String endcity = "终点";
    public static final String startkilo = "起始公里数";
    public static final String endkilo = "结束公里数";
    public static final String kilo = "行驶公里数";
    public static final String id = "序列号";
    public static final String number = "表单号";

    public static ArrayList<String> makeDepartColumnNameList() {
        ArrayList<String> departColumnNameList = new ArrayList<>();

        departColumnNameList.add(ConstantUtil.carnum);
        departColumnNameList.add(ConstantUtil.startcity);
        departColumnNameList.add(ConstantUtil.endcity);
        departColumnNameList.add(ConstantUtil.startkilo);
        departColumnNameList.add(ConstantUtil.endkilo);
        departColumnNameList.add(ConstantUtil.kilo);
        departColumnNameList.add(ConstantUtil.id);
        departColumnNameList.add(ConstantUtil.number);

        return departColumnNameList;
    }


    //导出 过路费 燃油费 罚款等，需要的表头名称
    public static final String month = "月份";
    public static final String runcount = "月度运行单程数";
    public static final String sumkilo = "总里程";
    public static final String avg = "平均里程";
    public static final String money = "月度总费用";

    public static LinkedList makeToolsTitle() {
        LinkedList<String> toolsTitleList = new LinkedList();
        toolsTitleList.add(month);
        toolsTitleList.add(runcount);
        toolsTitleList.add(sumkilo);
        toolsTitleList.add(avg);
        toolsTitleList.add(money);

        return toolsTitleList;
    }




    //导出 毛利 表头名称
    public static final String totalincome = "总收入";
    public static final String tolls = "过路费";
    public static final String fuel = "燃油费";
    public static final String fines = "罚款";
    public static final String parking = "停车费";
    public static final String tire = "轮胎费";
    public static final String repair = "维修费";
    public static final String other = "其他";
    public static final String totalcost = "总成本";


    public static LinkedList makeGrossTitle() {
        LinkedList<String> titleList = new LinkedList();
        titleList.add(month);
        titleList.add(runcount);
        titleList.add(sumkilo);
        titleList.add(totalincome);
        titleList.add(tolls);
        titleList.add(fuel);
        titleList.add(fines);
        titleList.add(parking);
        titleList.add(tire);
        titleList.add(repair);
        titleList.add(other);
        titleList.add(totalcost);

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
        incomeImportRowTitleList.add(title14);


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

}
