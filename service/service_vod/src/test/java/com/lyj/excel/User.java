package com.lyj.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class User {

    @ExcelProperty(value = "用户id",index = 0)
    private Integer id;

    @ExcelProperty(value = "用户名称",index = 1)
    private String name;
}
