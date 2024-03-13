package com.excel.pro.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseEntity {

    private String res;//success warning error
    private String resMessage;
    private String id;
    private List resList;
}
