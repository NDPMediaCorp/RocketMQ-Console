package com.alibaba.rocketmq.service;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.protocol.body.ConsumerConnection;
import com.alibaba.rocketmq.common.protocol.body.ProducerConnection;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-16
 */
@Service
public class ConnectionService {

    public ConsumerConnection getConsumerConnection(String group) throws Exception {
        ConsumerConnection cc = null;
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            defaultMQAdminExt.start();
            cc = defaultMQAdminExt.examineConsumerConnectionInfo(group);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return cc;
    }


    public ProducerConnection getProducerConnection(String group, String topicName) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        ProducerConnection pc = null;
        try {
            defaultMQAdminExt.start();
            pc = defaultMQAdminExt.examineProducerConnectionInfo(group, topicName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return pc;
    }
}
