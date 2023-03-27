package com.excel.pro.entity;

import lombok.Data;

import java.util.List;

@Data
public class TreeEntity {
    private Long id;
    private String label;
    private String num;
    private Long parentid;

    private List<TreeEntity> children;


}
