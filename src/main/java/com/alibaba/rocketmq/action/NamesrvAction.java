package com.alibaba.rocketmq.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.rocketmq.service.NamesrvService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-17
 */
@Controller
@RequestMapping("/namesrv")
public class NamesrvAction {

    private static final String FLAG = "namesrv_flag";

    @Autowired
    NamesrvService namesrvService;


    void putPublicAttribute(ModelMap map) {
        map.put(FLAG, "active");
    }


    @RequestMapping(value = "/updateKvConfig.do", method = RequestMethod.GET)
    public String updateKvConfig(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/updateKvConfig";
    }


    @RequestMapping(value = "/updateKvConfig.do", method = RequestMethod.POST)
    public String updateKvConfig(ModelMap map, @RequestParam String namespace, @RequestParam String key,
            @RequestParam String value) {
        putPublicAttribute(map);
        map.put("namespace", namespace);
        map.put("key", key);
        map.put("value", value);
        boolean res = false;
        try {
            res = namesrvService.updateKvConfig(namespace, key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", res);
        return "namesrv/updateKvConfig";
    }


    @RequestMapping(value = "/deleteKvConfig.do", method = RequestMethod.GET)
    public String deleteKvConfig(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/deleteKvConfig";
    }


    @RequestMapping(value = "/deleteKvConfig.do", method = RequestMethod.POST)
    public String deleteKvConfig(ModelMap map, @RequestParam String namespace, @RequestParam String key) {
        putPublicAttribute(map);
        map.put("namespace", namespace);
        map.put("key", key);
        boolean res = false;
        try {
            res = namesrvService.deleteKvConfig(namespace, key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", res);
        return "namesrv/deleteKvConfig";
    }


    @RequestMapping(value = "/getProjectGroup.do", method = RequestMethod.GET)
    public String getProjectGroup(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/getProjectGroup";
    }


    @RequestMapping(value = "/getProjectGroup.do", method = RequestMethod.POST)
    public String getProjectGroup(ModelMap map, @RequestParam(required = false) String ip,
            @RequestParam(required = false) String project) {
        putPublicAttribute(map);
        map.put("project", project);
        map.put("ip", ip);
        try {
            String result = namesrvService.getProjectGroup(ip, project);
            map.put("result", result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "namesrv/getProjectGroup";
    }


    @RequestMapping(value = "/updateProjectGroup.do", method = RequestMethod.GET)
    public String updateProjectGroup(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/updateProjectGroup";
    }


    @RequestMapping(value = "/updateProjectGroup.do", method = RequestMethod.POST)
    public String updateProjectGroup(ModelMap map, @RequestParam(required = false) String ip,
            @RequestParam(required = false) String project) {
        putPublicAttribute(map);
        map.put("project", project);
        map.put("ip", ip);
        boolean res = false;
        try {
            res = namesrvService.updateProjectGroup(ip, project);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", res);
        return "namesrv/updateProjectGroup";
    }


    @RequestMapping(value = "/deleteProjectGroup.do", method = RequestMethod.GET)
    public String deleteProjectGroup(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/deleteProjectGroup";
    }


    @RequestMapping(value = "/deleteProjectGroup.do", method = RequestMethod.POST)
    public String deleteProjectGroup(ModelMap map, @RequestParam(required = false) String ip,
            @RequestParam(required = false) String project) {
        putPublicAttribute(map);
        map.put("project", project);
        map.put("ip", ip);
        boolean res = false;
        try {
            res = namesrvService.deleteProjectGroup(ip, project);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", res);
        return "namesrv/deleteProjectGroup";
    }


    @RequestMapping(value = "/wipeWritePerm.do", method = RequestMethod.GET)
    public String wipeWritePerm(ModelMap map) {
        putPublicAttribute(map);
        return "namesrv/wipeWritePerm";
    }


    @RequestMapping(value = "/wipeWritePerm.do", method = RequestMethod.POST)
    public String wipeWritePerm(ModelMap map, @RequestParam String brokerName) {
        putPublicAttribute(map);
        map.put("brokerName", brokerName);
        List<Map<String, String>> list = null;
        try {
            list = namesrvService.wipeWritePerm(brokerName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        map.put("list", list);
        return "namesrv/wipeWritePerm";
    }
}
