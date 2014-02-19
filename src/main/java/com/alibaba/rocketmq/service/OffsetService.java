package com.alibaba.rocketmq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static com.alibaba.rocketmq.common.Tool.bool;
import static com.alibaba.rocketmq.common.Tool.str;
import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.common.UtilAll;
import com.alibaba.rocketmq.common.admin.RollbackStats;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;


/**
 * @see com.alibaba.rocketmq.tools.command.offset.ResetOffsetByTimeSubCommand
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-19
 */
@Service
public class OffsetService {

    public Table resetOffsetByTime(String consumerGroup, String topic, String timeStampStr, String forceStr)
            throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            long timestamp = 0;
            try {
                // 直接输入 long 类型的 timestamp
                timestamp = Long.valueOf(timeStampStr);
            }
            catch (NumberFormatException e) {
                // 输入的为日期格式，精确到毫秒
                timestamp = UtilAll.parseDate(timeStampStr, UtilAll.yyyy_MM_dd_HH_mm_ss_SSS).getTime();
            }

            boolean force = true;
            if (isNotBlank(forceStr)) {
                force = bool(forceStr.trim());
            }
            defaultMQAdminExt.start();
            List<RollbackStats> rollbackStatsList =
                    defaultMQAdminExt.resetOffsetByTimestamp(consumerGroup, topic, timestamp, force);
            // System.out
            // .printf(
            // "rollback consumer offset by specified consumerGroup[%s], topic[%s], force[%s], timestamp(string)[%s], timestamp(long)[%s]\n",
            // consumerGroup, topic, force, timeStampStr, timestamp);
            //
            // System.out.printf("%-20s  %-20s  %-20s  %-20s  %-20s  %-20s\n",//
            // "#brokerName",//
            // "#queueId",//
            // "#brokerOffset",//
            // "#consumerOffset",//
            // "#timestampOffset",//
            // "#rollbackOffset" //
            // );
            String[] thead =
                    new String[] { "#brokerName", "#queueId", "#brokerOffset", "#consumerOffset",
                                  "#timestampOffset", "#rollbackOffset" };
            Table table = new Table(thead, rollbackStatsList.size());

            for (RollbackStats rollbackStats : rollbackStatsList) {
                // System.out.printf("%-20s  %-20d  %-20d  %-20d  %-20d  %-20d\n",//
                // UtilAll.frontStringAtLeast(rollbackStats.getBrokerName(),
                // 32),//
                // rollbackStats.getQueueId(),//
                // rollbackStats.getBrokerOffset(),//
                // rollbackStats.getConsumerOffset(),//
                // rollbackStats.getTimestampOffset(),//
                // rollbackStats.getRollbackOffset() //
                // );
                String[] tr = table.createTR();
                tr[0] = UtilAll.frontStringAtLeast(rollbackStats.getBrokerName(), 32);
                tr[1] = str(rollbackStats.getQueueId());
                tr[2] = str(rollbackStats.getBrokerOffset());
                tr[3] = str(rollbackStats.getConsumerOffset());
                tr[4] = str(rollbackStats.getTimestampOffset());
                tr[5] = str(rollbackStats.getRollbackOffset());
                table.insertTR(tr);
            }
            return table;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            defaultMQAdminExt.shutdown();
        }
        return null;
    }
}
