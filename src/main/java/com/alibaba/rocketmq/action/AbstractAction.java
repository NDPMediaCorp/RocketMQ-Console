package com.alibaba.rocketmq.action;

import static com.alibaba.rocketmq.common.Contants.KEY_ACTION_RESULT;
import static com.alibaba.rocketmq.common.Contants.KEY_MSG;
import static com.alibaba.rocketmq.common.Contants.KEY_RES;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.cli.Option;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;

import com.google.common.collect.Maps;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-17
 */
public abstract class AbstractAction {

    protected abstract String getFlag();


    protected void putPublicAttribute(ModelMap map) {
        map.put(getFlag(), "active");
    }


    protected void backfillParam(HttpServletRequest request, ModelMap map) {
        @SuppressWarnings("unchecked")
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String key = enumer.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
    }


    @SuppressWarnings("unchecked")
    protected void addOptionValue(Collection<Option> options, String key, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            String tempVal = (String) value;
            if (StringUtils.isBlank(tempVal)) {
                return;
            }
        }
        for (Option opt : options) {
            if (opt.getLongOpt().equals(key)) {
                opt.getValuesList().add(value);
            }
        }
    }


    protected void checkOptions(Collection<Option> options) {
        for (Option option : options) {
            if (option.isRequired()) {
                String value = option.getValue();
                if (StringUtils.isBlank(value)) {
                    throw new IllegalStateException("option: key =[" + option.getLongOpt() + "], required=["
                            + option.isRequired() + "] is blank!");
                }
            }
        }
    }


    protected void putExpMsg(Exception e, ModelMap map) {
        Map<String, Object> expMap = Maps.newHashMap();
        expMap.put(KEY_MSG, e.getMessage());
        expMap.put(KEY_RES, false);
        map.put(KEY_ACTION_RESULT, expMap);
    }

}
