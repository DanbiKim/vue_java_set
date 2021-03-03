package com.playd.vue.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class IpAccessInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		/*
		List<String> ipList=new ArrayList<String>(); //허용 ip 리스트
		try {
			
			ipList.add("10.67.32.*"); //개발자
			ipList.add("10.67.31.*"); //일반사용자
			ipList.add("localhost"); //로컬
			ipList.add("127.0.0.1"); //로컬
			ipList.add("0:0:0:0:0:0:0:1"); //로컬
			ipList.add("10.101.97.88"); //개발서버
			ipList.add("10.101.97.61"); //실서버(사설)
			ipList.add("125.209.207.241"); //실서버(공인)
			ipList.add("61.37.123.*");
			
			String access_ip = StringUtil.getRemoteIp(request);
		//	System.out.println(access_ip);
			
			boolean access_chk=false;
			
			
			
			for(String ip : ipList) {
				if(access_ip.matches(ip)) {
					access_chk=true;
				}
			}
			
			if(!access_chk) {
				response.sendRedirect("/error/401-1");
				return false;
			}
			
			
		}catch(Exception e) {
			response.sendRedirect("/error/500");
			return false;
		}
		*/
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
	}
}

