package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartDetailDao extends BaseMapper<Departdetail> {
    public List<Departdetail> queryByid();

    DepartExportEntity exportDepart(@Param("cityname") String cityname);
}