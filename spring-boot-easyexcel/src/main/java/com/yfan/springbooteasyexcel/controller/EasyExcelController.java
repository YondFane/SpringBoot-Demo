package com.yfan.springbooteasyexcel.controller;

import com.yfan.springbooteasyexcel.service.IEasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("excel")
public class EasyExcelController {

    @Autowired
    private IEasyExcelService easyExcelService;

    /*
     * 导出excel表格
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param response
     * @return java.lang.String
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcel(response);
    }

    /*
     * 导出excel表格
     * @author YFAN
     * @date 2021/11/10/010
     * @param  * @param response
     * @return java.lang.String
     */
    @RequestMapping(value = "export2", method = RequestMethod.GET)
    @ResponseBody
    public void export2(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelUseClass(response);
    }

}
