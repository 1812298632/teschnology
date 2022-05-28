package com.excel.pro.service;

import com.excel.pro.entity.DepartExportEntity;
import com.excel.pro.entity.Departdetail;

import java.io.FileNotFoundException;
import java.util.List;

public interface DepartService {
    void insertDepartDetail(String sheetName) throws FileNotFoundException;

    List<Departdetail> querybyid();

    DepartExportEntity exportDepart(String cityname);
}
