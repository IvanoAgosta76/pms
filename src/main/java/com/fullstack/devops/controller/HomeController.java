package com.fullstack.devops.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Home Page Controller
	 * @return
	 */
	@RequestMapping("/pms")
	public String home() {
		logger.debug("Home page");
		return "home";
	}
}
