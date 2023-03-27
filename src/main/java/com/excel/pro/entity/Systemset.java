package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Systemset {

    @TableId
    private Long id;

    private Long num;
    private String title;
    private String parentid;
}
