package com.alibaba.rocketmq.action;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.common.protocol.route.TopicRouteData;
import com.alibaba.rocketmq.service.TopicService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-11
 */
@Controller
@RequestMapping("/topic")
public class TopicAction extends AbstractAction {

    static final Logger logger = LoggerFactory.getLogger(TopicAction.class);

    @Autowired
    TopicService topicService;

    protected String getFlag() {
        return "topic_flag";
    }


    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(ModelMap map) {
        putPublicAttribute(map);
        try {
            Table table = topicService.list();
            map.put("table", table);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "topic/list";
    }


    @RequestMapping(value = "/stats.do", method = RequestMethod.GET)
    public String stats(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        try {
            Table table = topicService.stats(topicName);
            map.put("table", table);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "topic/stats";
    }


    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add(ModelMap map) {
        putPublicAttribute(map);
        Collection<Option> options = topicService.getOptionsForUpdate();
        map.put("options", options);
        map.put("action", "update.do");
        return "topic/update";
    }


    @RequestMapping(value = "/route.do", method = RequestMethod.GET)
    public String route(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        try {
            TopicRouteData topicRouteData = topicService.route(topicName);
            map.put("topicRouteData", topicRouteData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "topic/route";
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.GET)
    public String delete(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        Collection<Option> options = topicService.getOptionsForDelete();
        map.put("options", options);
        map.put("action", "delete.do");
        addOptionValue(options, "topic", topicName);
        return "topic/delete";
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String delete(ModelMap map, @RequestParam String clusterName,
             @RequestParam String topic) {
        putPublicAttribute(map);
        Collection<Option> options = topicService.getOptionsForDelete();
        map.put("options", options);
        map.put("action", "delete.do");
        addOptionValue(options, "topic", topic);
        addOptionValue(options, "clusterName", clusterName);
        try {
            Map<String, Object> resMap = topicService.delete(topic, clusterName);
            map.put(KEY_RETURN, resMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "topic/delete";
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.GET)
    public String update(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        Collection<Option> options = topicService.getOptionsForUpdate();
        addOptionValue(options, "topic", topicName);
        map.put("options", options);
        map.put("action", "update.do");
        return "topic/update";
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String update(ModelMap map, @RequestParam String topic,
            @RequestParam(required = false) String readQueueNums,
            @RequestParam(required = false) String writeQueueNums,
            @RequestParam(required = false) String perm, 
            @RequestParam(required = false) String brokerAddr,
            @RequestParam(required = false) String clusterName) {
        putPublicAttribute(map);
        Collection<Option> options = topicService.getOptionsForUpdate();
        map.put("options", options);
        map.put("action", "update.do");
        addOptionValue(options, "topic", topic);
        addOptionValue(options, "readQueueNums", readQueueNums);
        addOptionValue(options, "writeQueueNums", writeQueueNums);
        addOptionValue(options, "perm", perm);
        addOptionValue(options, "brokerAddr", brokerAddr);
        addOptionValue(options, "clusterName", clusterName);
        try {
            Map<String, Object> resMap =
                    topicService.update(topic, readQueueNums, writeQueueNums, perm, brokerAddr, clusterName);
            map.put(KEY_RETURN, resMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "topic/update";
    }
}
