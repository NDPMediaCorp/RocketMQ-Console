package com.alibaba.rocketmq.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/topic")
public class TopicAction {

	@RequestMapping(value = "/index.do", method=RequestMethod.GET)
	public String index() {
		return "topic/index";
	}
	
	@RequestMapping(value = "/get.do", method=RequestMethod.GET)
	public String get() {
		return "topic/index";
	}
	
	@RequestMapping(value = "/put.do", method=RequestMethod.PUT)
	public String put() {
		return "topic/index";
	}
	
	@RequestMapping(value = "/update.do", method=RequestMethod.POST)
	public String update() {
		return "topic/index";
	}
	
	@RequestMapping(value = "/delete.do", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete() {
		return "topic/index";
	}
	
}
