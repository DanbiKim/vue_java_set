package com.playd.vue.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Autowired
	private TestMapper testMapper;

	public int selectServerCheck() {
		
		return testMapper.selectServerCheck();
	}
}
