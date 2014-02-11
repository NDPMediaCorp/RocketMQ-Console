package com.alibaba.rocketmq.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.TopicConfig;
import com.alibaba.rocketmq.common.UtilAll;
import com.alibaba.rocketmq.common.admin.TopicOffset;
import com.alibaba.rocketmq.common.admin.TopicStatsTable;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.body.TopicList;
import com.alibaba.rocketmq.domain.TopicBean;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-11
 */
@Service
public class TopicService {

    public List<TopicBean> list() throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        List<TopicBean> result = new ArrayList<TopicBean>();

        try {
            defaultMQAdminExt.start();
            TopicList topicList = defaultMQAdminExt.fetchAllTopicList();
            for (String topicName : topicList.getTopicList()) {
                TopicBean topicBean = new TopicBean();
                topicBean.setTopicName(topicName);
                result.add(topicBean);
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

    public List<TopicBean> stats(String topicName) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        List<TopicBean> result = new ArrayList<TopicBean>();
        try {
            defaultMQAdminExt.start();
            TopicStatsTable topicStatsTable = defaultMQAdminExt.examineTopicStats(topicName);

            List<MessageQueue> mqList = new LinkedList<MessageQueue>();
            mqList.addAll(topicStatsTable.getOffsetTable().keySet());
            Collections.sort(mqList);

//            System.out.printf("%-32s  %-4s  %-20s  %-20s    %s\n",//
//                "#Broker Name",//
//                "#QID",//
//                "#Min Offset",//
//                "#Max Offset",//
//                "#Last Updated" //
//            );

            for (MessageQueue mq : mqList) {
                TopicOffset topicOffset = topicStatsTable.getOffsetTable().get(mq);

                String humanTimestamp = "";
                if (topicOffset.getLastUpdateTimestamp() > 0) {
                    humanTimestamp = UtilAll.timeMillisToHumanString2(topicOffset.getLastUpdateTimestamp());
                }
                
                TopicBean topicBean = new TopicBean();
                topicBean.setTopicName(topicName);
                topicBean.setBrokerName(UtilAll.frontStringAtLeast(mq.getBrokerName(), 32));
                topicBean.setQid(mq.getQueueId());
                topicBean.setMinOffset(topicOffset.getMinOffset());
                topicBean.setMaxOffset( topicOffset.getMaxOffset());
                topicBean.setLastUpdated(humanTimestamp);
                
                result.add(topicBean);
//                System.out.printf("%-32s  %-4d  %-20d  %-20d    %s\n",//
//                    UtilAll.frontStringAtLeast(mq.getBrokerName(), 32),//
//                    mq.getQueueId(),//
//                    topicOffset.getMinOffset(),//
//                    topicOffset.getMaxOffset(),//
//                    humanTimestamp //
//                    );
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
    


    public void update(String topicName, String readQueueNums, String writeQueueNums, String perm) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));

        try {
            TopicConfig topicConfig = new TopicConfig();
            topicConfig.setReadQueueNums(8);
            topicConfig.setWriteQueueNums(8);
            topicConfig.setTopicName(topicName);
            
            if (StringUtils.isNotBlank(readQueueNums)) {
                topicConfig.setReadQueueNums(Integer.parseInt(readQueueNums));
            }
            
            if (StringUtils.isNotBlank(writeQueueNums)) {
                topicConfig.setWriteQueueNums(Integer.parseInt(writeQueueNums));
            }
            
            if (StringUtils.isNotBlank(perm)) {
                
            }
            
            defaultMQAdminExt.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
    }
}
