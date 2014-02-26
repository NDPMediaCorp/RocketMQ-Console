package com.alibaba.rocketmq.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.common.constant.PermName;
import com.alibaba.rocketmq.config.ConfigureInitializer;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import com.alibaba.rocketmq.tools.command.SubCommand;


public abstract class AbstractService {

    static final Map<String, Collection<Option>> cmd2option = new HashMap<String, Collection<Option>>();

    @Autowired
    ConfigureInitializer configureInitializer;
    
    protected DefaultMQAdminExt getDefaultMQAdminExt() {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        return defaultMQAdminExt;
    }


    protected void shutdownDefaultMQAdminExt(DefaultMQAdminExt defaultMQAdminExt) {
        defaultMQAdminExt.shutdown();
    }


    protected Collection<Option> getOptions(SubCommand subCommand) {
        String commandName = subCommand.commandName();
        Collection<Option> value = cmd2option.get(commandName);
        if (value == null) {
            Options options = new Options();
            subCommand.buildCommandlineOptions(options);
            @SuppressWarnings("unchecked")
            Collection<Option> col = options.getOptions();
            cmd2option.put(commandName, col);
            value = cmd2option.get(commandName);
        }
        return value;
    }


    protected int translatePerm(String perm) {
        if (perm.toLowerCase().equals("r")) {
            return PermName.PERM_READ;
        }
        else if (perm.toLowerCase().equals("w")) {
            return PermName.PERM_WRITE;
        }
        else {
            return PermName.PERM_READ & PermName.PERM_WRITE;
        }
    }


    protected Map<String, Object> getRuturnValue() {
        return new HashMap<String, Object>();
    }

    public static final String KEY_MSG = "key_msg";
    public static final String KEY_RES = "key_res";
}
