package com.alibaba.rocketmq.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.service.MessageService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-17
 */
@Controller
@RequestMapping("/message")
public class MessageAction extends AbstractAction {

    @Autowired
    MessageService messageService;


    protected String getFlag() {
        return "message_flag";
    }


    @RequestMapping(value = "/queryMsgById.do", method = RequestMethod.GET)
    public String queryMsgById(ModelMap map) {
        putPublicAttribute(map);
        return "message/queryMsgById";
    }


    @RequestMapping(value = "/queryMsgById.do", method = RequestMethod.POST)
    public String queryMsgById(ModelMap map, @RequestParam String msgId) {
        putPublicAttribute(map);
        map.put("msgId", msgId);
        Table result = null;
        try {
            result = messageService.queryMsgById(msgId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result", result);
        return "message/queryMsgById";
    }


    @RequestMapping(value = "/queryMsgByKey.do", method = RequestMethod.GET)
    public String queryMsgByKey(ModelMap map) {
        putPublicAttribute(map);
        return "message/queryMsgById";
    }


    @RequestMapping(value = "/queryMsgByKey.do", method = RequestMethod.POST)
    public String queryMsgByKey(ModelMap map, @RequestParam String topicName, @RequestParam String msgKey,
            @RequestParam String fallbackHours) {
        putPublicAttribute(map);
        map.put("topicName", topicName);
        map.put("msgKey", msgKey);
        map.put("fallbackHours", fallbackHours);
        Table result = null;
        try {
            result = messageService.queryMsgByKey(topicName, msgKey, fallbackHours);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result", result);
        return "message/queryMsgByKey";
    }
    
    @RequestMapping(value = "/queryMsgByOffset.do", method = RequestMethod.GET)
    public String queryMsgByOffset(ModelMap map) {
        putPublicAttribute(map);
        return "message/queryMsgByOffset";
    }


    @RequestMapping(value = "/queryMsgByOffset.do", method = RequestMethod.POST)
    public String queryMsgByOffset(ModelMap map, @RequestParam String topicName, @RequestParam String brokerName,
            @RequestParam String queueId, @RequestParam String offset) {
        putPublicAttribute(map);
        map.put("topicName", topicName);
        map.put("brokerName", brokerName);
        map.put("queueId", queueId);
        map.put("offset", offset);
        Table result = null;
        try {
            result = messageService.queryMsgByOffset(topicName, brokerName, queueId, offset);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result", result);
        return "message/queryMsgByKey";
    }
}
