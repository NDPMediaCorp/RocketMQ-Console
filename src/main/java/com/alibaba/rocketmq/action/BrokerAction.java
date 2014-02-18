package com.alibaba.rocketmq.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.service.BrokerService;


@Controller
@RequestMapping("/broker")
public class BrokerAction extends AbstractAction {

    @Autowired
    BrokerService brokerService;


    protected String getFlag() {
        return "broker_flag";
    }


    @RequestMapping(value = "/brokerStats.do", method = RequestMethod.GET)
    public String brokerStats(ModelMap map) {
        putPublicAttribute(map);
        return "broker/brokerStats";
    }


    @RequestMapping(value = "/brokerStats.do", method = RequestMethod.POST)
    public String brokerStats(ModelMap map, @RequestParam String brokerAddr) {
        putPublicAttribute(map);
        map.put("brokerAddr", brokerAddr);
        Table table = null;
        try {
            table = brokerService.brokerStats(brokerAddr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("table", table);
        return "broker/brokerStats";
    }


    @RequestMapping(value = "/updateBrokerConfig.do", method = RequestMethod.GET)
    public String updateBrokerConfig(ModelMap map) {
        putPublicAttribute(map);
        return "broker/updateBrokerConfig";
    }
    
    @RequestMapping(value = "/updateBrokerConfig.do", method = RequestMethod.POST)
    public String updateBrokerConfig(ModelMap map, @RequestParam String brokerAddr,
            @RequestParam String clusterName, String key, String value) {
        putPublicAttribute(map);
        map.put("brokerAddr", brokerAddr);
        map.put("clusterName", clusterName);
        map.put("key", key);
        map.put("value", value);
        boolean msg = false;
        try {
            msg = brokerService.updateBrokerConfig(brokerAddr, clusterName, key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", msg);
        return "broker/updateBrokerConfig";
    }
}
