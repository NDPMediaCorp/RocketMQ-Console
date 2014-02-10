package com.alibaba.rocketmq.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.common.protocol.body.ClusterInfo;
import com.alibaba.rocketmq.common.protocol.body.KVTable;
import com.alibaba.rocketmq.common.protocol.route.BrokerData;
import com.alibaba.rocketmq.domain.ClusterBean;
import com.alibaba.rocketmq.domain.ClusterBean.BrokerBase;
import com.alibaba.rocketmq.domain.ClusterBean.BrokerBase.BrokerEntity;
import com.alibaba.rocketmq.remoting.exception.RemotingConnectException;
import com.alibaba.rocketmq.remoting.exception.RemotingSendRequestException;
import com.alibaba.rocketmq.remoting.exception.RemotingTimeoutException;
import com.alibaba.rocketmq.tools.admin.MQAdminExt;


/**
 * 集群服务类
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-8
 */
@Service
public class ClusterService {

    @Autowired
    MQAdminExt defaultMQAdminExt;


    public List<ClusterBean> listAllBasicInfo() throws InterruptedException, MQBrokerException,
            RemotingTimeoutException, RemotingSendRequestException, RemotingConnectException {

        ClusterInfo clusterInfoSerializeWrapper = defaultMQAdminExt.examineBrokerClusterInfo();
        // System.out.printf("%-16s  %-32s  %-4s  %-22s %-22s %11s %11s\n",//
        // "#Cluster Name",//
        // "#Broker Name",//
        // "#BID",//
        // "#Addr",//
        // "#Version",//
        // "#InTPS",//
        // "#OutTPS"//
        // );

        List<ClusterBean> result = new ArrayList<ClusterBean>();

        Iterator<Map.Entry<String, Set<String>>> itCluster =
                clusterInfoSerializeWrapper.getClusterAddrTable().entrySet().iterator();
        while (itCluster.hasNext()) {
            Map.Entry<String, Set<String>> next = itCluster.next();
            String clusterName = next.getKey();
            Set<String> brokerNameSet = new HashSet<String>();
            brokerNameSet.addAll(next.getValue());

            ClusterBean clusterBean = new ClusterBean();//
            clusterBean.setClusterName(clusterName);//
            Set<BrokerBase> brokerBaseSet = new HashSet<BrokerBase>();//
            clusterBean.setBrokerBaseSet(brokerBaseSet);
            ;//

            result.add(clusterBean);//

            for (String brokerName : brokerNameSet) {
                BrokerBase brokerBase = new BrokerBase();//
                brokerBase.setBrokerName(brokerName);//
                Set<BrokerEntity> brokerEntitySet = new HashSet<BrokerEntity>();//
                brokerBase.setBrokerEntitySet(brokerEntitySet);//

                brokerBaseSet.add(brokerBase);//

                BrokerData brokerData = clusterInfoSerializeWrapper.getBrokerAddrTable().get(brokerName);
                if (brokerData != null) {
                    Iterator<Map.Entry<Long, String>> itAddr =
                            brokerData.getBrokerAddrs().entrySet().iterator();
                    while (itAddr.hasNext()) {

                        Map.Entry<Long, String> next1 = itAddr.next();
                        double in = 0;
                        double out = 0;
                        String version = "";

                        long InTotalYest = 0;
                        long OutTotalYest = 0;
                        long InTotalToday = 0;
                        long OutTotalToday = 0;

                        try {
                            KVTable kvTable = defaultMQAdminExt.fetchBrokerRuntimeStats(next1.getValue());
                            String putTps = kvTable.getTable().get("putTps");
                            String getTransferedTps = kvTable.getTable().get("getTransferedTps");
                            version = kvTable.getTable().get("brokerVersionDesc");
                            {
                                String[] tpss = putTps.split(" ");
                                if (tpss != null && tpss.length > 0) {
                                    in = Double.parseDouble(tpss[0]);
                                }
                            }

                            {
                                String[] tpss = getTransferedTps.split(" ");
                                if (tpss != null && tpss.length > 0) {
                                    out = Double.parseDouble(tpss[0]);
                                }
                            }

                            BrokerEntity brokerEntity = new BrokerEntity();//
                            brokerEntitySet.add(brokerEntity);//

                            brokerEntity.setBid(next1.getKey().longValue());//
                            brokerEntity.setAddr(next1.getValue());//
                            brokerEntity.setVersion(version);//
                            brokerEntity.setInTPS(in);//
                            brokerEntity.setOutTPS(out);//

                            String msgPutTotalYesterdayMorning =
                                    kvTable.getTable().get("msgPutTotalYesterdayMorning");
                            String msgPutTotalTodayMorning =
                                    kvTable.getTable().get("msgPutTotalTodayMorning");
                            String msgPutTotalTodayNow = kvTable.getTable().get("msgPutTotalTodayNow");
                            String msgGetTotalYesterdayMorning =
                                    kvTable.getTable().get("msgGetTotalYesterdayMorning");
                            String msgGetTotalTodayMorning =
                                    kvTable.getTable().get("msgGetTotalTodayMorning");
                            String msgGetTotalTodayNow = kvTable.getTable().get("msgGetTotalTodayNow");

                            InTotalYest =
                                    Long.parseLong(msgPutTotalTodayMorning)
                                            - Long.parseLong(msgPutTotalYesterdayMorning);
                            OutTotalYest =
                                    Long.parseLong(msgGetTotalTodayMorning)
                                            - Long.parseLong(msgGetTotalYesterdayMorning);

                            InTotalToday =
                                    Long.parseLong(msgPutTotalTodayNow)
                                            - Long.parseLong(msgPutTotalTodayMorning);
                            OutTotalToday =
                                    Long.parseLong(msgGetTotalTodayNow)
                                            - Long.parseLong(msgGetTotalTodayMorning);

                            brokerEntity.setInTotalYest(InTotalYest);//
                            brokerEntity.setOutTotalYest(OutTotalYest);
                            brokerEntity.setInTotalToday(InTotalToday);
                            brokerEntity.setOutTotalToday(OutTotalToday);
                        }
                        catch (Exception e) {
                        }
                        //
                        // System.out.printf("%-16s  %-32s  %-4s  %-22s %-22s %11.2f %11.2f\n",//
                        // clusterName,//
                        // brokerName,//
                        // next1.getKey().longValue(),//
                        // next1.getValue(),//
                        // version,//
                        // in,//
                        // out//
                        // );

                        // System.out.printf("%-16s  %-32s %14d %14d %14d %14d\n",//
                        // clusterName,//
                        // brokerName,//
                        // InTotalYest,//
                        // OutTotalYest,//
                        // InTotalToday,//
                        // OutTotalToday//
                        // );
                    }
                }
            }
        }
        return result;
    }

}
