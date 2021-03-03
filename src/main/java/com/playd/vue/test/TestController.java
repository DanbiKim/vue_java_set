package com.playd.vue.test;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playd.vue.common.JsonResponse;


@RestController
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private TestService testService;
	
	/*서버체크*/
	@RequestMapping("/server-chk")
	public JsonResponse serverCheck(Model model){
		
		boolean resultVal = false;
		String errorMsg="";
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("success", resultVal);
		resultMap.put("message", errorMsg);
	
		try {
			
			resultVal =  testService.selectServerCheck() > 0;
			
			
		} catch (Exception e) {
			
			errorMsg = e.getMessage();
			
		}
	
		return new JsonResponse(resultVal).setMessage(errorMsg);
	
		  
	}
}