package com.yfan.springbooteasyexcel.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IEasyExcelService {
    public static String BEAN = "IEasyExcelService";

    public void exportExcel(HttpServletResponse response) throws IOException;

    public void exportExcelUseClass(HttpServletResponse response) throws IOException;
}
