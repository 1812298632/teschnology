package com.excel.pro.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.lang.reflect.InvocationTargetException;

public interface UploadService {
    void uploadNewCar(XSSFSheet sheet);

    void uploadMainline(XSSFSheet sheet);

    void uploadCarCount(XSSFSheet sheet);

    void uploadSystemSet(XSSFSheet sheet) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
