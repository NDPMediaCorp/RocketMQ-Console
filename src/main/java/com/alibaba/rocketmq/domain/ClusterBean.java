package com.alibaba.rocketmq.domain;

import java.io.Serializable;


/**
 * 集群信息Bean
 * 
 * @author yankai913@gmail.com
 * @since 2014-2-7
 */
public class ClusterBean implements Serializable {

    private static final long serialVersionUID = -7518412644035256109L;

    /** 集群的名字 */
    private String clusterName;

    /** broker的名字 */
    private String brokerName;

    /** broker的id */
    private String bid;

    /** 节点的IP */
    private String addr;

    /** 当前版本 */
    private String version;

    /** 当前节点到目前为止输入消息的TPS */
    private String inTPS;

    /** 当前节点到目前为止输出消息的TPS */
    private String outTPS;

    /** 当前节点昨天一整天输入消息的TPS */
    private String inTotalYest;

    /** 当前节点昨天一整天输出消息的TPS */
    private String outTotalYest;

    /** 当前节点今天一整天输出消息的TPS */
    private String inTotalToday;

    /** 当前节点今天一整天输出消息的TPS */
    private String outTotalToday;


    public String getClusterName() {
        return clusterName;
    }


    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }


    public String getBrokerName() {
        return brokerName;
    }


    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }


    public String getBid() {
        return bid;
    }


    public void setBid(String bid) {
        this.bid = bid;
    }


    public String getAddr() {
        return addr;
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }


    public String getInTPS() {
        return inTPS;
    }


    public void setInTPS(String inTPS) {
        this.inTPS = inTPS;
    }


    public String getOutTPS() {
        return outTPS;
    }


    public void setOutTPS(String outTPS) {
        this.outTPS = outTPS;
    }


    public String getInTotalYest() {
        return inTotalYest;
    }


    public void setInTotalYest(String inTotalYest) {
        this.inTotalYest = inTotalYest;
    }


    public String getOutTotalYest() {
        return outTotalYest;
    }


    public void setOutTotalYest(String outTotalYest) {
        this.outTotalYest = outTotalYest;
    }


    public String getInTotalToday() {
        return inTotalToday;
    }


    public void setInTotalToday(String inTotalToday) {
        this.inTotalToday = inTotalToday;
    }


    public String getOutTotalToday() {
        return outTotalToday;
    }


    public void setOutTotalToday(String outTotalToday) {
        this.outTotalToday = outTotalToday;
    }

}
