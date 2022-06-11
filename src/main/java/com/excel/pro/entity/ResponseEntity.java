package com.excel.pro.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResponseEntity {

    private String res;//success warning error
    private String resMessage;
    private List resList;
}
