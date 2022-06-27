package com.excel.pro.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;
import com.excel.pro.entity.ResponseEntity;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileNotFoundException;
import java.util.List;

public interface DepartService {
    void insertDepartDetail(String sheetName) throws FileNotFoundException;

    List<Departdetail> querybyid();

    DepartExportEntity exportDepart(String cityname, String cartype);

    void insertDepartDeatilByUpload(XSSFSheet sheet);

    void update(Departdetail departdetail, UpdateWrapper<Departdetail> updateWrapper, ResponseEntity responseEntity);

    void delete(LambdaQueryWrapper<Departdetail> queryWrapper, ResponseEntity responseEntity);
}
