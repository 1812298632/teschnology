package com.excel.pro.entity;

import lombok.Data;

/**
 * 用户表表实体类
 */
@Data
public class SysUser {
    private String id;
    private String username;
    private String password;
    private Long state;
}
