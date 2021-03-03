package com.playd.vue.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
	
	@RequestMapping(value={"/","/router/**"} ,method = {RequestMethod.GET, RequestMethod.HEAD})
	public String index(HttpServletRequest req) {
		System.out.println("~~index");

		return "index";
	}

	//테스트 페이지 용
	@RequestMapping(value={"/template/{any_page}"} ,method = {RequestMethod.GET, RequestMethod.HEAD})
	public String template(HttpServletRequest req, @PathVariable(value = "any_page") String any_page) {
		System.out.println("~~template");

		return "/template/"+any_page;
	}
}
