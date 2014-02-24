package com.alibaba.rocketmq.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

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


    public void backfillParam(HttpServletRequest request, ModelMap map) {
        @SuppressWarnings("unchecked")
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String key = enumer.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
    }
}
