package com.alibaba.rocketmq.service;

import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;

public abstract class AbstractService {

    public DefaultMQAdminExt getDefaultMQAdminExt() {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        return defaultMQAdminExt;
    }


    public void shutdownDefaultMQAdminExt(DefaultMQAdminExt defaultMQAdminExt) {
        defaultMQAdminExt.shutdown();
    }
}
