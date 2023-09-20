package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("CARCOUNT")
public class Carcount {
    public String network;
    public String num;
    public String length;
    public Integer year;
    public Integer month;

}
