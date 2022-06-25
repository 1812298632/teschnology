package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartDetailDao extends BaseMapper<Departdetail> {
    public List<Departdetail> queryByid();

    DepartExportEntity exportDepart(@Param("cityname") String cityname, @Param("cartype")String cartype);

    List<SelectEntity> queryCitySelect();

    List<SelectEntity> queryEndCitySelect();

    List<SelectEntity> queryJFMonthSelect();

    List<SelectEntity> queryCarTypeByDepart();

    List<SelectEntity> queryJfCount(@Param("cartype")String cartype);

    List<Incomestatement> queryjfmoney(@Param("cartype")String cartype);

    List<IndexPageEntity> selectCountGroupByMonth();


    List<IndexPageEntity> selectCountClassify();

    List<IndexPageEntity> queryDepartWarring();

    List<IndexPageEntity> queryincomeIndex();

}
