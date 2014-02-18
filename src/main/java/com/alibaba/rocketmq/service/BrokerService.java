package com.alibaba.rocketmq.service;

import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.common.protocol.body.KVTable;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import com.alibaba.rocketmq.tools.command.CommandUtil;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-18
 */
@Service
public class BrokerService {

    public Table brokerStats(String brokerAddr) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            defaultMQAdminExt.start();
            KVTable kvTable = defaultMQAdminExt.fetchBrokerRuntimeStats(brokerAddr);
            TreeMap<String, String> tmp = new TreeMap<String, String>();
            tmp.putAll(kvTable.getTable());
            return Table.Map2VTable(tmp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return null;
    }


    public boolean updateBrokerConfig(String brokerAddr, String clusterName, String key, String value)
            throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            Properties properties = new Properties();
            properties.put(key, value);
            if (StringUtils.isNotBlank(brokerAddr)) {
                defaultMQAdminExt.start();
                defaultMQAdminExt.updateBrokerConfig(brokerAddr, properties);
                return true;
            } else if (StringUtils.isNotBlank(clusterName)) {
                defaultMQAdminExt.start();
                Set<String> masterSet =
                        CommandUtil.fetchMasterAddrByClusterName(defaultMQAdminExt, clusterName);
                for (String tempBrokerAddr : masterSet) {
                    defaultMQAdminExt.updateBrokerConfig(brokerAddr, properties);
                    System.out.printf("update broker config success, %s\n", tempBrokerAddr);
                }
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return false;
    }
}
