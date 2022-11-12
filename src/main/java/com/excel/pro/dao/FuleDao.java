package com.excel.pro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excel.pro.entity.Fule;
import com.excel.pro.entity.SelectEntity;

import java.util.List;

public interface FuleDao extends BaseMapper<Fule> {
    List<SelectEntity> queryFuleType();

    List<SelectEntity> queryFuleYear();

    List<SelectEntity> quertFuleColumns();

}
