package com.excel.pro.entity;

import lombok.Data;

@Data
public class IncomeExportEntity {
    private String sheet;
    private String month;
    private String kilosum;
    private Long  runcount;
    private Double avg;
    private Double money;

    private Double avgonway;//平均单程费用
    private Double cost;//每公里成本


    //毛利
    private Double totalincomemoney;//总收入
    private Double tollsmoney;//过路费
    private Double fuelmoney;//燃油费
    private Double finesmoney;//罚款
    private Double parkingmoney;//停车费
    private Double tiremoney;//轮胎费
    private Double repairmoney;//维修费
    private Double othermoney;//其他
    private Double totalcostmoney;//总成本


    private Double kilocost;//公里成本费用
    private Double gross;//毛利
    private Double grossrate;//毛利率
    private Double grossonway;//单程毛利
}
