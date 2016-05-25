/**
 * Project Name:istart
 * File Name:WelcomeController.java
 * Package Name:com.istart.framework.web.controller
 * Date:2016年5月24日上午9:15:08
 * Copyright (c) 2016, xhujinjun@163.com All Rights Reserved.
 *
 */

package com.istart.framework.web.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: WelcomeController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月24日 上午9:15:08 <br/>
 *
 * @author xiejinjun
 * @version 
 * @since JDK 1.6
 */
@Controller
public class WelcomeController {
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", "Hello Spring Boot Beetl!");
		return "welcome";
	}

}


