package com.yfan.springbootcommon.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ExportWordUtil {

    /*
     * 导出word
     * @author yfanfan
     * @date 2022-06-20 09:00
     * @param  * @param request
     * @param response
     * @param ftlPath classpath:templates下的ftl路径
     * @param exportWordVO
     * @return void
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String ftlPath, Map map) throws Exception {
        Writer writerOut = null;
        try {
            File file = ResourceUtils.getFile("classpath:templates");
            log.error("CreateDOCController downloadDCOC pathFTL:{}", file);
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(file);
            Template template = configuration.getTemplate(ftlPath, "utf-8");
            String fileName = URLEncoder.encode(map.getOrDefault("fileName", "defaultFileName") + ".docx", "UTF-8");
            log.error("CreateDOCController downloadDCOC fileName:{}", fileName);
            // 输出到response
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            writerOut = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            template.process(map, writerOut);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (writerOut != null) {
                writerOut.close();
            }
        }
    }

    /*
     * 导出word
     * @author yfanfan
     * @date 2022-06-20 09:00
     * @param  * @param request
     * @param response
     * @param ftlPath classpath:templates下的ftl模板路径
     * @param exportWordVO
     * @return void
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String ftlPath, Object exportWordVO) throws Exception {
        Writer writerOut = null;
        try {
            Map<String, Object> map = beanToMap(exportWordVO);
            File file = ResourceUtils.getFile("classpath:templates");
            log.error("CreateDOCController downloadDCOC pathFTL:{}", file);
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(file);
            Template template = configuration.getTemplate(ftlPath, "utf-8");
            String fileName = URLEncoder.encode(map.getOrDefault("fileName", "defaultFileName") + ".docx", "UTF-8");
            log.error("CreateDOCController downloadDCOC fileName:{}", fileName);
            // 输出到response
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            writerOut = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            template.process(map, writerOut);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (writerOut != null) {
                writerOut.close();
            }
        }
    }

    /*
     * 导出word
     * @author yfanfan
     * @date 2022-06-20 09:00
     * @param  * @param request
     * @param response
     * @param ftlPath classpath:templates下的ftl模板路径
     * @param savePath 保存路径
     * @param exportWordVO
     * @return void
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String ftlPath, String savePath, Object exportWordVO) throws Exception {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        Writer writerOut = null;
        try {
            Map<String, Object> map = beanToMap(exportWordVO);
            File file = ResourceUtils.getFile("classpath:templates");
            log.error("CreateDOCController downloadDCOC pathFTL:{}", file);
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(file);
            Template template = configuration.getTemplate(ftlPath, "utf-8");
            //生成到一个临时路径
            String fileName = URLEncoder.encode(map.getOrDefault("fileName", "defaultFileName") + ".pdf", "UTF-8");
            log.error("CreateDOCController downloadDCOC fileName:{}", fileName);
            String outFileUrl = savePath + fileName;
            File outFile = new File(outFileUrl);
            writerOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            template.process(map, writerOut);
            log.error("CreateDOCController downloadDOC savePath:{}", outFileUrl);
            // 输出到response
            File f = new File(outFileUrl);
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            in = new BufferedInputStream(new FileInputStream(f));
            out = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (writerOut != null) {
                writerOut.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /*
     * 对象转Map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
}
