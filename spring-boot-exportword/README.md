# 主要解决<mark>低版本poi jar包</mark>的导出word问题

## 涉及到word内容的动态变化

使用到的依赖为：

```xml
<dependency>
<groupId>org.apache.poi</groupId>
<artifactId>poi</artifactId>
<version>3.10.1</version>
</dependency>
<dependency>
<groupId>org.freemarker</groupId>
<artifactId>freemarker</artifactId>
<version>2.3.29</version>
</dependency>
```

准备：

1、首先在word上编辑好内容；

2、使用WPS或office另存为XML格式；

3、文件后缀名从.xml修改为.ftl；

4、将ftl模板文件复制到resouce下的template目录下。



**<mark>如果可以使用高版本poi包，例如poi-4.1.2 jar包以上，可以使用poi-tl实现导出word。</mark>**

**<mark>poi-tl可以使用word模板进行动态赋值导出word。</mark>**

以下为poi-tl的简单实现：

```java
public class Test {

    public static void main(String[] args) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "张三");
        data.put("start_time", "2022-06-18");
        XWPFTemplate template = XWPFTemplate.compile("E:/template.docx")
                .render(data);
        FileOutputStream out;
        out = new FileOutputStream("E:/template2.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
```



### 启动访问

localhost:8080/exportWord/test