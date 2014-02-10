package com.alibaba.rocketmq.config;

import java.util.Properties;

import com.alibaba.rocketmq.common.MixAll;


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
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY,
            configureProps.getProperty(MixAll.NAMESRV_ADDR_PROPERTY));
    }
}
