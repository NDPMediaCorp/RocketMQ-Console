package com.alibaba.rocketmq.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * 集群信息Bean<br>
 * <li>同一个名称的集群可能包含多个名称的broker</li> <li>同一个名称的broker可能包含多个broker实体，例如主备</li>
 * 
 * @author yankai913@gmail.com
 * @since 2014-2-7
 */
public class ClusterBean implements Serializable {

    private static final long serialVersionUID = -7518412644035256109L;

    /** 集群的名字 */
    private String clusterName;

    private Set<BrokerBase> brokerBaseSet;


    @Override
    public String toString() {
        return "{clusterName:" + clusterName + ", brokerBaseSet:" + brokerBaseSet + "}";
    }


    public String getClusterName() {
        return clusterName;
    }


    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }


    public Set<BrokerBase> getBrokerBaseSet() {
        return brokerBaseSet;
    }


    public void setBrokerBaseSet(Set<BrokerBase> brokerBaseSet) {
        this.brokerBaseSet = brokerBaseSet;
    }

    public static class BrokerBase implements Serializable {

        private static final long serialVersionUID = 7300920038636469894L;

        /** broker的名字 */
        private String brokerName;

        private Set<BrokerEntity> brokerEntitySet;


        @Override
        public String toString() {
            return "{brokerName:" + brokerName + ", brokerEntitySet:" + brokerEntitySet + "}";
        }


        public String getBrokerName() {
            return brokerName;
        }


        public void setBrokerName(String brokerName) {
            this.brokerName = brokerName;
        }


        public Set<BrokerEntity> getBrokerEntitySet() {
            return brokerEntitySet;
        }


        public void setBrokerEntitySet(Set<BrokerEntity> brokerEntitySet) {
            this.brokerEntitySet = brokerEntitySet;
        }

        public static class BrokerEntity implements Serializable {

            private static final long serialVersionUID = 4872461731088921026L;

            /** broker的id，通过编号区分主备 */
            private long bid;

            /** 节点的IP */
            private String addr;

            /** 当前版本 */
            private String version;

            /** 当前节点到目前为止输入消息的TPS */
            private double inTPS;

            /** 当前节点到目前为止输出消息的TPS */
            private double outTPS;

            /** 当前节点昨天一整天输入消息的TPS */
            private long inTotalYest;

            /** 当前节点昨天一整天输出消息的TPS */
            private long outTotalYest;

            /** 当前节点今天一整天输出消息的TPS */
            private long inTotalToday;

            /** 当前节点今天一整天输出消息的TPS */
            private long outTotalToday;

            @Override
            public String toString() {
                return "{bid:" + bid + ", addr:" + addr + ", version:" + version 
                            + ", inTPS:" + inTPS + ", outTPS:" + outTPS + ", inTotalYest:" + inTotalYest
                            + ", outTotalYest:" + outTotalYest + ", inTotalToday:" + inTotalToday
                            + ", outTotalToday:" + inTotalToday;
            }
            
            public long getBid() {
                return bid;
            }


            public void setBid(long bid) {
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


            public double getInTPS() {
                return inTPS;
            }


            public void setInTPS(double inTPS) {
                this.inTPS = inTPS;
            }


            public double getOutTPS() {
                return outTPS;
            }


            public void setOutTPS(double outTPS) {
                this.outTPS = outTPS;
            }


            public long getInTotalYest() {
                return inTotalYest;
            }


            public void setInTotalYest(long inTotalYest) {
                this.inTotalYest = inTotalYest;
            }


            public long getOutTotalYest() {
                return outTotalYest;
            }


            public void setOutTotalYest(long outTotalYest) {
                this.outTotalYest = outTotalYest;
            }


            public long getInTotalToday() {
                return inTotalToday;
            }


            public void setInTotalToday(long inTotalToday) {
                this.inTotalToday = inTotalToday;
            }


            public long getOutTotalToday() {
                return outTotalToday;
            }


            public void setOutTotalToday(long outTotalToday) {
                this.outTotalToday = outTotalToday;
            }
        }
    }
}
