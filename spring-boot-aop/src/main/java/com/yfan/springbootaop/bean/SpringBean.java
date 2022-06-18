package com.yfan.springbootaop.bean;


import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@MyAnnotation
@Component
public class SpringBean implements BeanNameAware {

    @Value("ID")
    private String id;

    public SpringBean() {

    }

    public SpringBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        System.out.println("SpringBean--id属性注入");
        this.id = id;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("SpringBean--BeanNameAware--setBeanName()--"+name);
    }
}
