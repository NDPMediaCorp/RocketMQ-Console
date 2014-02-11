package com.alibaba.rocketmq.config;

import java.util.Enumeration;
import java.util.Properties;


/**
 * 把需要补充的初始化环境变量正式的放入系统属性中
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-10
 * 
 */
public class ConfigureInitializer {

    private Properties configureProps;


    public Properties getConfigureProps() {
        return configureProps;
    }


    public void setConfigureProps(Properties configureProps) {
        this.configureProps = configureProps;
    }


    public void init() {
        @SuppressWarnings("unchecked")
        Enumeration<String> enume = (Enumeration<String>) configureProps.propertyNames();
        while (enume.hasMoreElements()) {
            String key = enume.nextElement();
            System.setProperty(key, configureProps.getProperty(key));
        }
    }
}
