package com.banry.pscm.workflow.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringUtil implements ApplicationContextAware {  

    /** 
     * 当前IOC 
     *  
     */  
    private static ApplicationContext applicationContext;  

    /** 
     * * 设置当前上下文环境，此方法由spring自动装配 
     *  
     */  
    @Override  
    public void setApplicationContext(ApplicationContext arg0)  
            throws BeansException {  
        applicationContext = arg0;  
    }  

    /** 
     * 从当前IOC获取bean 
     * @param <T>
     */  
    public static <T> Object getObject(Class<T> type) {
        return applicationContext.getBean(type);  
    }  
}