package com.alibaba.rocketmq.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.protocol.route.TopicRouteData;
import com.alibaba.rocketmq.config.ConfigureInitializer;
import com.alibaba.rocketmq.domain.TopicBean;
import com.alibaba.rocketmq.service.TopicService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-11
 */
@Controller
@RequestMapping("/topic")
public class TopicAction extends AbstractAction {

    @Autowired
    TopicService topicService;

    @Autowired
    ConfigureInitializer configureInitializer;


    protected String getFlag() {
        return "topic_flag";
    }


    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(ModelMap map) {
        putPublicAttribute(map);
        try {
            List<TopicBean> topicBeanList = topicService.list();
            map.put("topicBeanList", topicBeanList);
            System.out.println(topicBeanList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/list";
    }


    @RequestMapping(value = "/stats.do", method = RequestMethod.GET)
    public String stats(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        try {
            List<TopicBean> topicBeanList = topicService.stats(topicName);
            map.put("topicBeanList", topicBeanList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/stats";
    }


    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add(ModelMap map) {
        putPublicAttribute(map);
        return "topic/add";
    }


    @RequestMapping(value = "/route.do", method = RequestMethod.GET)
    public String route(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        try {
            TopicRouteData topicRouteData =
                    topicService.route(topicName, configureInitializer.getNamesrvAddr());
            map.put("topicRouteData", topicRouteData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/route";
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.GET)
    public String delete(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        map.put("topicName", topicName);
        String[] namesrvAddrArr = configureInitializer.getNamesrvAddr().split(",");
        map.put("namesrvAddrArr", namesrvAddrArr);
        return "topic/delete";
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String delete(ModelMap map, @RequestParam String clusterName,
            @RequestParam(required = false) String nameServer, @RequestParam String topicName) {
        putPublicAttribute(map);
        try {
            topicService.delete(topicName, clusterName, nameServer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:list.do";
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.GET)
    public String update(ModelMap map, @RequestParam String topicName) {
        putPublicAttribute(map);
        map.put("topicName", topicName);
        return "topic/update";
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String update(ModelMap map, @RequestParam String topicName, @RequestParam String readQueueNums,
            @RequestParam String writeQueueNums, @RequestParam String perm, @RequestParam String brokerAddr,
            @RequestParam(required = false) String clusterName) {
        putPublicAttribute(map);
        try {
            topicService.update(topicName, readQueueNums, writeQueueNums, perm, brokerAddr, clusterName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:list.do";
    }
}
