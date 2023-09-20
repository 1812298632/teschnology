package com.excel.pro.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface UploadService {
    void uploadNewCar(XSSFSheet sheet);

    void uploadMainline(XSSFSheet sheet);

    void uploadCarCount(XSSFSheet sheet);
}
