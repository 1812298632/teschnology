package com.excel.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.excel.pro.dao.SystemSetDao;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.entity.Systemset;
import com.excel.pro.util.ConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class SystemSetServiceImpl implements com.excel.pro.service.SystemSetService {

    @Resource
    private SystemSetDao systemSetDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Systemset systemset, ResponseEntity responseEntity) {

        if(StringUtils.isEmpty(systemset.getTitle())){
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("title不能为空");
            throw new RuntimeException("title不能为空");
        }


        int insert = systemSetDao.insert(systemset);

        if (insert != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预新增效果");
            throw new RuntimeException("未达到预新增除果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("新增成功" + insert + "条数据");

        }


    }

    @Override
    public void updateSystem(Systemset systemset, ResponseEntity responseEntity) {

        LambdaQueryWrapper<Systemset> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Systemset::getId,systemset.getId());

        int update =  systemSetDao.updateById(systemset);



        if (update != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预修改效果");
            throw new RuntimeException("未达到预修改效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("修改成功" + update + "条数据");

        }
    }

    @Override
    public void deleteSystem(Systemset systemset, ResponseEntity responseEntity) {
        int delete =  systemSetDao.deleteById(systemset.getId());


        if (delete != 1) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage("未达到预删除效果");
            throw new RuntimeException("未达到预删除效果");
        } else {
            responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);
            responseEntity.setResMessage("删除成功" + delete + "条数据");

        }
    }
}
