package com.excel.pro.entity;

import lombok.Data;

@Data
public class Departdetail {
    private String cartype;// 沃尔沃/解放车

    private String carnum;//派单车号
    private String startcity;
    private String endcity;
    private Long startkilo;
    private Long endkilo;
    private Long kilo;
    private String sheet;//sheet名称
    private Long year;//年份
    private Long sheetid;//sheet中的序列
    private Long month;//月份
    private String fromno;//中卡物流车辆作业台账 excel中的表单号列
    private String excelname;//数据来自哪个excel中
    private String ismonthcount;
    private String iskilosum;
}
