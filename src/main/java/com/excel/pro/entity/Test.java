package com.excel.pro.entity;

import lombok.Data;

@Data
public class Test {

    public String formno;
    public String startcity;
    public String endcity;


    public String getFormno() {
        return formno;
    }

    public void setFormno(String formno) {
        this.formno = formno;
    }

    public String getStartcity() {
        return startcity;
    }

    public void setStartcity(String startcity) {
        this.startcity = startcity;
    }

    public String getEndcity() {
        return endcity;
    }

    public void setEndcity(String endcity) {
        this.endcity = endcity;
    }
}
