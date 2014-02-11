package com.alibaba.rocketmq.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.domain.TopicBean;
import com.alibaba.rocketmq.service.TopicService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-11
 */
@Controller
@RequestMapping("/topic")
public class TopicAction {

    @Autowired
    TopicService topicService;


    void putPublicAttribute(ModelMap map) {
        map.put("topic_flag", "active");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "topic/stats";
    }


    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add(ModelMap map) {
        putPublicAttribute(map);
        return "topic/add";
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String update(ModelMap map) {
        return "topic/index";
    }


    @RequestMapping(value = "/delete.do", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete() {
        return "topic/index";
    }

}
