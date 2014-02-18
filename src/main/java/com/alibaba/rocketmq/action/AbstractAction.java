package com.alibaba.rocketmq.action;

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
    
}
