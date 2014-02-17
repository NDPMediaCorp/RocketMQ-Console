package com.alibaba.rocketmq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.namesrv.NamesrvUtil;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-17
 */
@Service
public class NamesrvService {

    public boolean deleteKvConfig(String namespace, String key) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            defaultMQAdminExt.start();
            defaultMQAdminExt.deleteKvConfig(namespace, key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return true;
    }


    public boolean deleteProjectGroup(String ip, String project) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        String namespace = NamesrvUtil.NAMESPACE_PROJECT_CONFIG;
        try {
            if (StringUtils.isNotBlank(ip)) {
                defaultMQAdminExt.start();
                defaultMQAdminExt.deleteKvConfig(namespace, ip);
            }
            else if (StringUtils.isNotBlank(project)) {
                defaultMQAdminExt.start();
                defaultMQAdminExt.deleteIpsByProjectGroup(project);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return true;
    }


    public String getProjectGroup(String ip, String project) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            if (StringUtils.isNotBlank(ip)) {
                defaultMQAdminExt.start();
                String projectInfo = defaultMQAdminExt.getProjectGroupByIp(ip);
                return projectInfo;
            }
            else if (StringUtils.isNotBlank(project)) {
                defaultMQAdminExt.start();
                String ips = defaultMQAdminExt.getIpsByProjectGroup(project);
                return ips;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return null;
    }


    public boolean updateKvConfig(String namespace, String key, String value) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            defaultMQAdminExt.start();
            defaultMQAdminExt.createAndUpdateKvConfig(namespace, key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return true;
    }


    public boolean updateProjectGroup(String ip, String project) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        String namespace = NamesrvUtil.NAMESPACE_PROJECT_CONFIG;
        try {
            defaultMQAdminExt.start();
            defaultMQAdminExt.createAndUpdateKvConfig(namespace, ip, project);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return true;
    }


    public List<Map<String, String>> wipeWritePerm(String brokerName) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        List<Map<String, String>> result = null;
        try {
            defaultMQAdminExt.start();
            List<String> namesrvList = defaultMQAdminExt.getNameServerAddressList();
            if (namesrvList != null) {
                result = new ArrayList<Map<String, String>>();
                for (String namesrvAddr : namesrvList) {
                    try {
                        int wipeTopicCount = defaultMQAdminExt.wipeWritePermOfBroker(namesrvAddr, brokerName);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("brokerName", brokerName);
                        map.put("namesrvAddr", namesrvAddr);
                        map.put("wipeTopicCount", String.valueOf(wipeTopicCount));
                        result.add(map);
                        // System.out.printf("wipe write perm of broker[%s] in name server[%s] OK, %d\n",//
                        // brokerName,//
                        // namesrvAddr,//
                        // wipeTopicCount//
                        // );
                    }
                    catch (Exception e) {
                        System.out.printf("wipe write perm of broker[%s] in name server[%s] Failed\n",//
                            brokerName,//
                            namesrvAddr//
                            );

                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return result;
    }
}
