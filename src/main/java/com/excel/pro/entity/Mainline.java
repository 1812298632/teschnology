package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("MAINLINE")
public class Mainline {

    public double num;
    public String formno;
    public String carnum;
    public String platenum;
    public Double fuelmoney;
    public Double tollmoney;
    public Double incomemoney;
    public Double finemoney1;
    public Double finemoney2;
    public Double finemoney3;
    public Double finemoney4;
    public Long year;
    public Long month;
    public String startcity;

}
