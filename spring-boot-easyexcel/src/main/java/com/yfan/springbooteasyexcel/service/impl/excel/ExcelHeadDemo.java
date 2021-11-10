package com.yfan.springbooteasyexcel.service.impl.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.Setter;

@Data
public class ExcelHeadDemo {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("年龄")
    private String age;
}
