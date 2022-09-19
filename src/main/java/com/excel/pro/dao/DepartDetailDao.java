package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartDetailDao extends BaseMapper<Departdetail> {
    public List<Departdetail> queryByid();

    DepartExportEntity exportDepart(@Param("cityname") String cityname, @Param("cartype")String cartype,@Param("year")String year);

    List<SelectEntity> queryCitySelect();

    List<SelectEntity> queryEndCitySelect();

    List<SelectEntity> queryMonthSelect();

    List<SelectEntity> queryCarTypeByDepart();

    List<SelectEntity> queryRunCount(@Param("cartype")String cartype,@Param("year")String year);

    List<Incomestatement> queryIncomeMoney(@Param("cartype")String cartype,@Param("year")String year);

    List<IndexPageEntity> selectCountGroupByMonth();


    List<IndexPageEntity> selectCountClassify();

    List<IndexPageEntity> queryDepartWarring();

    List<IndexPageEntity> queryincomeIndex();

    List<SelectEntity> queryYearSelect();

}
