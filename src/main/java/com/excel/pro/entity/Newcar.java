package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("NEWCAR")
public class Newcar {
    public String network;
    public String carnum;
    public String startdate;
    public String wangdian;
    public Double weight;
    public Double volume;
    public Double money;
    public Double cost;
    public String remark;
    public Long year;

}
