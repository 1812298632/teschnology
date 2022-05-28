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

}
