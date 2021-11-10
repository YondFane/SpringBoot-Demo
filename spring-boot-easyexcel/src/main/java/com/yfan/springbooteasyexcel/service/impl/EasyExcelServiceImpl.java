package com.yfan.springbooteasyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.yfan.springbooteasyexcel.service.IEasyExcelService;
import com.yfan.springbooteasyexcel.service.impl.excel.ExcelHeadDemo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service(IEasyExcelService.BEAN)
public class EasyExcelServiceImpl implements IEasyExcelService {

    /*
     * 导出excel表格
     * 简单
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param response
     * @return void
     */
    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 设置响应头信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");


        // 第一步 获取ExcelWriterBuilder
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream());
        // 第二步 创建excel表格的工作簿
        ExcelWriterSheetBuilder excelWriterSheetBuilder = excelWriterBuilder.sheet("模板");
        // 第三步 将内容写入excel的工作簿中
        excelWriterSheetBuilder.doWrite(data());
    }

    /*
     * 模拟excel表格数据
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param
     * @return java.util.ArrayList<java.lang.String>
     */
    private ArrayList<ArrayList<String>> data() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        // 表头
        ArrayList<String> headList = new ArrayList<>();
        headList.add("姓名");
        headList.add("性别");
        headList.add("年龄");
        list.add(headList);
        // 内容
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("张三");
        dataList.add("男");
        dataList.add("20");
        list.add(dataList);
        return list;
    }

    /*
     * 导出excel表格
     * 使用实体对象
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param response
     * @return void
     */
    @Override
    public void exportExcelUseClass(HttpServletResponse response) throws IOException {
        // 设置响应头信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");


        // 第一步 获取ExcelWriterBuilder 传入excel对应的实体对象
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream(), ExcelHeadDemo.class);
        // 第二步 创建excel表格的工作簿
        ExcelWriterSheetBuilder excelWriterSheetBuilder = excelWriterBuilder.sheet("模板");
        // 第三步 将内容写入excel的工作簿中
        excelWriterSheetBuilder.doWrite(dataUseClass());
    }

    /*
     * 模拟excel表格数据
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param
     * @return java.util.ArrayList<java.lang.String>
     */
    private ArrayList<ExcelHeadDemo> dataUseClass() {
        ArrayList<ExcelHeadDemo> list = new ArrayList<>();
        // 内容
        ExcelHeadDemo excelHeadDemo = new ExcelHeadDemo();
        excelHeadDemo.setName("张三");
        excelHeadDemo.setGender("男");
        excelHeadDemo.setAge("120");
        list.add(excelHeadDemo);
        return list;
    }
}
