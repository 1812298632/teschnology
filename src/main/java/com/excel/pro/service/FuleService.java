package com.excel.pro.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.excel.pro.entity.Fule;
import com.excel.pro.entity.ResponseEntity;

public interface FuleService {
    void update(Fule fule, LambdaQueryWrapper<Fule> fuleWrapper, ResponseEntity responseEntity);

    void delete( LambdaQueryWrapper<Fule> fuleWrapper, ResponseEntity responseEntity);

    void insert(Fule fule, ResponseEntity responseEntity);

}
