package com.excel.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.excel.pro.dao.FuleDao;
import com.excel.pro.entity.Fule;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.service.FuleService;
import com.excel.pro.util.ConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FuleServiceImpl implements FuleService {
    @Resource
    private FuleDao fuleDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Fule fule, LambdaQueryWrapper<Fule> fuleWrapper, ResponseEntity responseEntity) {
        int update = fuleDao.update(fule, fuleWrapper);
        if (update != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预期修改效果");
            //未修改到想要数据 抛出异常，并回滚
            throw new RuntimeException("未达到预期修改效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("修改成功" + update + "条数据");

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete( LambdaQueryWrapper<Fule> fuleWrapper, ResponseEntity responseEntity) {
        int delete = fuleDao.delete(fuleWrapper);
        if (delete != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预期删除效果");
            //未修改到想要数据 抛出异常，并回滚
            throw new RuntimeException("未达到预期删除效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("删除成功" + delete + "条数据");

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Fule fule, ResponseEntity responseEntity) {
        int insert = fuleDao.insert(fule);
        if (insert != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预期删除效果");
            throw new RuntimeException("未达到预期删除效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("新增成功" + insert + "条数据");

        }
    }
}
