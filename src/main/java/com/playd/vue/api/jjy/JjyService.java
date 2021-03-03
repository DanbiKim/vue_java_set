package com.playd.vue.api.jjy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JjyService{
	
	@Autowired
	private JjyMapper jjyMapper;	
	
	
	// 거래사 상세정보 조회
	public List<JjyModel> selectBoardList(JjyModel jjyModel) {
		
		return jjyMapper.selectBoardList(jjyModel);
	}

	
}