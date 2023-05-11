package com.lyj.excel;

import com.alibaba.excel.EasyExcel;

public class readtestmain {
    public static void main(String[] args) {
        String fileurl = "D:\\软件目录\\esay.xlsx";
        EasyExcel.read(fileurl, User.class,new lisensetest()).sheet().doRead();
    }
}
