package com.alibaba.rocketmq.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.service.ClusterService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-8
 */
@Controller
@RequestMapping("/cluster")
public class ClusterAction extends AbstractAction {

    static final Logger logger = LoggerFactory.getLogger(ClusterAction.class);

    @Autowired
    ClusterService clusterService;


    @Override
    protected String getFlag() {
        return "cluster_flag";
    }


    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(ModelMap map) {
        putPublicAttribute(map);
        try {
            Table table = clusterService.list();
            map.put("table", table);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "cluster/list";
    }
}
