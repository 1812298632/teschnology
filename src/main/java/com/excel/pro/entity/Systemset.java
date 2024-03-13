package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Systemset {

    @TableId
    private Long id;

    private Long num;
    private String title;
    private Long parentid;
    private String startrow;
    private String startcolumn;
    private String valuetype;
    private String cellvalue;
    private String tablename;
    private String columnname;
    private String classname;

}
