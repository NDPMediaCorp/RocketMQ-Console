package com.alibaba.rocketmq.action;

import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.cli.Option;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;


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
            String tempVal = (String)value;
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

    protected static final String KEY_RETURN = "resMap";
}
