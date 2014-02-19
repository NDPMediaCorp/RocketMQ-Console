package com.alibaba.rocketmq.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.service.ConsumerService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-18
 */
@Controller
@RequestMapping("/consumer")
public class ConsumerAction extends AbstractAction {

    @Autowired
    ConsumerService consumerService;


    @Override
    protected String getFlag() {
        return "consumer_flag";
    }


    @RequestMapping(value = "/consumerProgress.do", method = RequestMethod.GET)
    public String consumerProgress(ModelMap map) {
        putPublicAttribute(map);
        return "consumer/consumerProgress";
    }


    @RequestMapping(value = "/consumerProgress.do", method = RequestMethod.POST)
    public String consumerProgress(ModelMap map, @RequestParam String consumerGroup) {
        putPublicAttribute(map);
        map.put("consumerGroup", consumerGroup);
        try {
            Table table = consumerService.consumerProgress(consumerGroup);
            map.put("result", table);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "consumer/consumerProgress";
    }


    @RequestMapping(value = "/deleteSubGroup.do", method = RequestMethod.GET)
    public String deleteSubGroup(ModelMap map) {
        putPublicAttribute(map);
        return "consumer/deleteSubGroup";
    }


    @RequestMapping(value = "/deleteSubGroup.do", method = RequestMethod.POST)
    public String deleteSubGroup(ModelMap map, @RequestParam String groupName,
            @RequestParam(required = false) String brokerAddr,
            @RequestParam(required = false) String clusterName) {
        putPublicAttribute(map);
        map.put("groupName", groupName);
        map.put("brokerAddr", brokerAddr);
        map.put("clusterName", clusterName);
        boolean result = false;
        try {
            result = consumerService.deleteSubGroup(groupName, brokerAddr, clusterName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", result);
        return "consumer/deleteSubGroup";
    }


    @RequestMapping(value = "/updateSubGroup.do", method = RequestMethod.GET)
    public String updateSubGroup(ModelMap map) {
        putPublicAttribute(map);
        return "consumer/updateSubGroup";
    }


    @RequestMapping(value = "/updateSubGroup.do", method = RequestMethod.POST)
    public String updateSubGroup(ModelMap map, @RequestParam(required = false) String brokerAddr,
            @RequestParam(required = false) String clusterName,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String consumeEnable,
            @RequestParam(required = false) String consumeFromMinEnable,
            @RequestParam(required = false) String consumeBroadcastEnable,
            @RequestParam(required = false) String retryQueueNums,
            @RequestParam(required = false) String retryMaxTimes,
            @RequestParam(required = false) String brokerId,
            @RequestParam(required = false) String whichBrokerWhenConsumeSlowly) {
        putPublicAttribute(map);
        map.put("brokerAddr", brokerAddr);
        map.put("clusterName", clusterName);
        map.put("groupName", groupName);
        map.put("consumeEnable", consumeEnable);
        map.put("consumeFromMinEnable", consumeFromMinEnable);
        map.put("consumeBroadcastEnable", consumeBroadcastEnable);
        map.put("retryQueueNums", retryQueueNums);
        map.put("retryMaxTimes", retryMaxTimes);
        map.put("brokerId", brokerId);
        map.put("whichBrokerWhenConsumeSlowly", whichBrokerWhenConsumeSlowly);
        boolean result = false;
        try {
            result =
                    consumerService.updateSubGroup(brokerAddr, clusterName, groupName, consumeEnable,
                        consumeFromMinEnable, consumeBroadcastEnable, retryQueueNums, retryMaxTimes,
                        brokerId, whichBrokerWhenConsumeSlowly);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", result);
        return "consumer/updateSubGroup";
    }

}
