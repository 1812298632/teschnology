package com.excel.pro.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

@Data
public class Fule {
    private Long year;
    private String cartype;
    private String columnname;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double onemonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double twomonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double threemonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double fourmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double fivemonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double sixmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double sevenmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double eightmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double ninemonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double tenmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double eleventmonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double twelvemonth;

    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.NUMERIC)
    private Double kilosum;
}
