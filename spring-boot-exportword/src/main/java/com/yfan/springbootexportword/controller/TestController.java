package com.yfan.springbootexportword.controller;

import com.yfan.springbootcommon.entity.UserVO;
import com.yfan.springbootcommon.util.ExportWordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class TestController {

    @RequestMapping("test")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 使用map
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName","导出word文件名");
        map.put("name", "张三");
        map.put("id", "ID");
        ExportWordUtil.exportWord(request, response, "wordTemplate.ftl", map);
    }

    @RequestMapping("test2")
    @ResponseBody
    public void test2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 使用对象的形式
        UserVO userVO = UserVO.builder().id("ID").name("李四").build();
        ExportWordUtil.exportWord(request, response, "wordTemplate.ftl", userVO);
    }

    @RequestMapping("test3")
    @ResponseBody
    public void test3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 使用对象的形式
        UserVO userVO = UserVO.builder().id("ID").name("李四").build();
        ExportWordUtil.exportWord(request, response, "document.ftl", userVO);
    }
}
