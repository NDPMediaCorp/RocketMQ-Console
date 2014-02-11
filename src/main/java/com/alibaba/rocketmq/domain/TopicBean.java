package com.alibaba.rocketmq.domain;

import java.io.Serializable;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-11
 */
public class TopicBean implements Serializable {

    private static final long serialVersionUID = 891871197460822975L;

    private String topicName;

    private String brokerName;

    private long qid;

    private long minOffset;

    private long maxOffset;

    private String lastUpdated;


    public long getQid() {
        return qid;
    }


    public void setQid(long qid) {
        this.qid = qid;
    }


    public String getTopicName() {
        return topicName;
    }


    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }


    public String getBrokerName() {
        return brokerName;
    }


    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }


    public long getMinOffset() {
        return minOffset;
    }


    public void setMinOffset(long minOffset) {
        this.minOffset = minOffset;
    }


    public long getMaxOffset() {
        return maxOffset;
    }


    public void setMaxOffset(long maxOffset) {
        this.maxOffset = maxOffset;
    }


    public String getLastUpdated() {
        return lastUpdated;
    }


    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    @Override
    public String toString() {
        return "topicName:" + topicName + ", brokerName:" + brokerName + ", qid:" + qid + ", minOffset:"
                + minOffset + ", maxOffset:" + maxOffset + ", lastUpdated:" + lastUpdated;
    }
}
