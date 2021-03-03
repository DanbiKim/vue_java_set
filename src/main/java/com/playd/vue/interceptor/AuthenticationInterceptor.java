package com.playd.vue.interceptor;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.playd.vue.member.MemberController;
import com.playd.vue.member.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.playd.vue.util.StringUtil;


public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MemberController beanMemberController;

	@Value("${spring.profiles.active}")
	private String springProfilesActive;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String origin = request.getHeader("Origin");		
		System.out.println("request_uri : "+request.getRequestURI());
		System.out.println("Origin : "+origin);
		System.out.println("profiles : "+springProfilesActive );
		System.out.println("IP : "+StringUtil.getRemoteIp(request) );
		String redirectUrl=request.getRequestURI();
		String ip = StringUtil.getRemoteIp(request);

		if(ip.indexOf("10.67.")>-1 || ip.indexOf("0:0:0:0:0:0:0:1")>-1) { // 로컬 테스트용
			return true;
		}

		HttpSession session = request.getSession(false);
		MemberModel member = null;
		if(session!=null) {
			member= (MemberModel)session.getAttribute("loginInfo");
		}
		String user_no=StringUtil.getCookie(request, "user_no");

		if(member ==null || member.getUser_no() < 0 || StringUtil.isEmpty(member.getUser_id())){
			System.out.println("인증 안됨");
			if(user_no!=null && !user_no.equals("")) {
				//세션은 끊겼지만, user_no가 있어서 서버 재시작으로 봄
				System.out.println("세션끊김.재로그인 시도");
				return beanMemberController.autoLoginInfo(response, request, StringUtil.decrypt(user_no));

			}else {
				if(redirectUrl.indexOf("/api/")>-1){ // restful 요청 일시엔 권한 없음 json 으로 가야함
					response.sendRedirect("/error/401");
					return false;
				}else{
					String redirectUrlParameter="";

					//파라메타 다 받아오는 것으로 변경
					Enumeration params = request.getParameterNames();
					int param_cnt=0;
					while (params.hasMoreElements()){
						param_cnt++;

						String param_type="";
						if(param_cnt==1) param_type="?";
						else param_type="&";

						String param_name  = (String)params.nextElement();
						redirectUrlParameter+=param_type+param_name+"="+request.getParameter(param_name);
					}

					String returnUrl= URLEncoder.encode(redirectUrl,"UTF-8");

					if(!redirectUrlParameter.equals("")) { //파라메타 붙이기
						returnUrl += URLEncoder.encode(redirectUrlParameter,"UTF-8");
					}

					String url = "/user/login?rUrl=";

					url += returnUrl;

					response.sendRedirect(url);
					return false;
				}


			}
		}else{
			System.out.println("인증 됨 - 사용자 : "+member.getUser_nm());
		}

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
	}
}

