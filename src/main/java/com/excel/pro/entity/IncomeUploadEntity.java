package com.excel.pro.entity;

import lombok.Data;

@Data
public class IncomeUploadEntity {
    private String type;
    private String cartype;
    private String sheetname;
    private String excelname;
    private String year;
}
