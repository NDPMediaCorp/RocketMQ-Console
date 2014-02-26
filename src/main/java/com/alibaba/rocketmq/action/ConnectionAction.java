package com.alibaba.rocketmq.action;

import java.util.Collection;

import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ConnectionAction extends AbstractAction {

    static final Logger logger = LoggerFactory.getLogger(ConnectionAction.class);

    @Autowired
    ConnectionService connectionService;


    @Override
    protected String getFlag() {
        return "connection_flag";
    }


    @RequestMapping(value = "/consumerConnection.do", method = RequestMethod.GET)
    public String consumerConnection(ModelMap map) {
        putPublicAttribute(map);
        Collection<Option> options = connectionService.getOptionsForGetConsumerConnection();
        map.put("options", options);
        map.put("action", "consumerConnection.do");
        return "conn/consumerConnection";
    }


    @RequestMapping(value = "/consumerConnection.do", method = RequestMethod.POST)
    public String consumerConnection(ModelMap map, @RequestParam String consumerGroup) {
        putPublicAttribute(map);
        Collection<Option> options = connectionService.getOptionsForGetConsumerConnection();
        map.put("options", options);
        map.put("action", "consumerConnection.do");
        addOptionValue(options, "consumerGroup", consumerGroup);
        try {
            checkOptions(options);
            ConsumerConnection cc = connectionService.getConsumerConnection(consumerGroup);
            map.put("cc", cc);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            putExpMsg(e, map);
        }
        return "conn/consumerConnection";
    }


    @RequestMapping(value = "/producerConnection.do", method = RequestMethod.GET)
    public String producerConnection(ModelMap map) {
        putPublicAttribute(map);
        Collection<Option> options = connectionService.getOptionsForGetProducerConnection();
        map.put("options", options);
        map.put("action", "producerConnection.do");
        return "conn/producerConnection";
    }


    @RequestMapping(value = "/producerConnection.do", method = RequestMethod.POST)
    public String producerConnection(ModelMap map, @RequestParam String producerGroup,
            @RequestParam String topic) {
        putPublicAttribute(map);
        Collection<Option> options = connectionService.getOptionsForGetProducerConnection();
        map.put("options", options);
        map.put("action", "producerConnection.do");
        addOptionValue(options, "producerGroup", producerGroup);
        addOptionValue(options, "topic", topic);
        try {
            checkOptions(options);
            ProducerConnection pc = connectionService.getProducerConnection(producerGroup, topic);
            map.put("pc", pc);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            putExpMsg(e, map);
        }
        return "conn/producerConnection";
    }
}
