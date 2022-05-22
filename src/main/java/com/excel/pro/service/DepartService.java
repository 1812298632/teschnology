package com.excel.pro.service;

import java.io.FileNotFoundException;

public interface DepartService {
    void insertDepartDetail(String sheetName) throws FileNotFoundException;
}
