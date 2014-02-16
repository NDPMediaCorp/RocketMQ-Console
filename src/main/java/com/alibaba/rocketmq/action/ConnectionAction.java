package com.alibaba.rocketmq.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.protocol.body.ConsumerConnection;
import com.alibaba.rocketmq.common.protocol.body.ProducerConnection;
import com.alibaba.rocketmq.service.ConnectionService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-16
 */
@Controller
@RequestMapping("/conn")
public class ConnectionAction {

    private static final String FLAG = "connection_flag";

    @Autowired
    ConnectionService connectionService;


    void putPublicAttribute(ModelMap map) {
        map.put(FLAG, "active");
    }


    @RequestMapping(value = "/consumerConnection.do", method = RequestMethod.GET)
    public String consumerConnection(ModelMap map) {
        putPublicAttribute(map);
        return "conn/consumerConnection";
    }


    @RequestMapping(value = "/consumerConnection.do", method = RequestMethod.POST)
    public String consumerConnection(ModelMap map, @RequestParam String group) {
        putPublicAttribute(map);
        map.put("group", group);
        try {
            ConsumerConnection cc = connectionService.getConsumerConnection(group);
            map.put("cc", cc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "conn/consumerConnection";
    }


    @RequestMapping(value = "/producerConnection.do", method = RequestMethod.GET)
    public String producerConnection(ModelMap map) {
        putPublicAttribute(map);
        return "conn/producerConnection";
    }


    @RequestMapping(value = "/producerConnection.do", method = RequestMethod.POST)
    public String producerConnection(ModelMap map, @RequestParam String group, @RequestParam String topicName) {
        putPublicAttribute(map);
        map.put("group", group);
        map.put("topicName", topicName);
        try {
            ProducerConnection pc = connectionService.getProducerConnection(group, topicName);
            map.put("pc", pc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "conn/producerConnection";
    }
}
