package com.excel.pro.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.entity.Systemset;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileNotFoundException;
import java.util.List;

public interface SystemSetService {
   public void insert(Systemset systemset, ResponseEntity responseEntity);

    void updateSystem(Systemset systemset, ResponseEntity responseEntity);

    void deleteSystem(Systemset systemset, ResponseEntity responseEntity);

}
