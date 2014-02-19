package com.alibaba.rocketmq.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.service.OffsetService;


/**
 * 
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-19
 */
@Controller
@RequestMapping("/offset")
public class OffsetAction extends AbstractAction {

    @Autowired
    OffsetService offsetService;


    @Override
    protected String getFlag() {
        return "offset_flag";
    }


    @RequestMapping(value = "/resetOffsetByTime.do", method = RequestMethod.GET)
    public String resetOffsetByTime(ModelMap map) {
        putPublicAttribute(map);
        return "offset/resetOffsetByTime";
    }


    @RequestMapping(value = "/resetOffsetByTime.do", method = RequestMethod.POST)
    public String resetOffsetByTime(ModelMap map, @RequestParam String group, @RequestParam String topic,
            @RequestParam String timestamp, @RequestParam(required = false) String force) {
        putPublicAttribute(map);
        map.put("group", group);
        map.put("topic", topic);
        map.put("timestamp", timestamp);
        map.put("force", force);
        try {
            Table table = offsetService.resetOffsetByTime(group, topic, timestamp, force);
            map.put("table", table);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "offset/resetOffsetByTime";
    }
}
