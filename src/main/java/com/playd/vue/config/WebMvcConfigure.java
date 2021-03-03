package com.playd.vue.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.playd.vue.interceptor.AuthenticationInterceptor;
import com.playd.vue.interceptor.IpAccessInterceptor;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
	
	@Autowired IpAccessInterceptor ipAccessInterceptor;
	
	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
//			.allowedOrigins("http://localhost:8080","https://plum3.playd.com")
			.allowedOrigins("*")
			.allowedMethods("POST","GET","PUT","DELETE")
			.allowCredentials(false)
			.maxAge(1000);
    }
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludeCommonList = new ArrayList<String>(); //공통 exclude
		excludeCommonList.add("/static/**");
		excludeCommonList.add("/asset/**");
		excludeCommonList.add("/template/**");
		excludeCommonList.add("/user/**");
		excludeCommonList.add("/error/**");
		excludeCommonList.add("/imgOnload/*");
		excludeCommonList.add("/server-chk");

		registry.addInterceptor(authenticationInterceptor())
				.addPathPatterns("/**")
		 		.excludePathPatterns(excludeCommonList);
	}
	
	//임시로 잠시 주석
	//로그인 등등 interceptor 재정의 필요
	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// 	//ip 허용 인터셉터 처리
	// 	registry.addInterceptor(ipAccessInterceptor)
	// 	.addPathPatterns("/**")
	// 	.excludePathPatterns("/error/**");
				
				
	// 	List<String> excludeCommonList = new ArrayList<String>(); //공통 exclude
	// 	excludeCommonList.add("/");
	// 	excludeCommonList.add("/static/**");
	// 	excludeCommonList.add("/login");
	// 	excludeCommonList.add("/error/**");
	// 	excludeCommonList.add("/imgOnload/*");
	// 	excludeCommonList.add("/server-chk");
		
	// 	List<String> excludeSwaggerList = new ArrayList<String>(); //test용 swagger
	// 	excludeSwaggerList.add("/swagger**");
	// 	excludeSwaggerList.add("/webjars/**");
	// 	excludeSwaggerList.add("/swagger-resources/**");
		
		
	// 	//공통 인터셉터 처리
	// 	registry.addInterceptor(authenticationInterceptor())
	// 	.addPathPatterns("/**")
	// 	.excludePathPatterns(excludeCommonList)
	// 	.excludePathPatterns(excludeSwaggerList);
		
		
	// }
	
}

