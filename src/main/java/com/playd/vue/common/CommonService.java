package com.playd.vue.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playd.vue.common.constants.Constants;


@Service
public class CommonService {
	
	@Autowired
	private CommonMapper commonMapper;
	
	public void insertWhatever(HashMap<String,Object> paramMap) {
		commonMapper.insertWhatever(paramMap);
	}
	
	public List<HashMap<String, Object>> selectCommCd(HashMap<String, Object> paramBody){
		return commonMapper.selectCommCd(paramBody); 
	}
	
	
	// 테이블or컬럼 존재여부 확인
	public boolean selectExistTable(SearchModel searchModel) {
		int exsitCnt = 0;
		exsitCnt = commonMapper.selectExistTable(searchModel);
		return exsitCnt > 0 ? true : false;  
	}
	
	//검색내용가져오기
	public List<HashMap<String, Object>> selectTable(SearchModel searchModel) {
		return commonMapper.selectTable(searchModel);
	}
	
	
	public boolean chkExceptTable(String tableNm) {
		boolean returnval = true;
		String[] except_tb = Constants.CRUD_EXCEPT_TABLE;
		for(String tb: except_tb) {
			if(tb.equals(tableNm)){
				returnval = false;
			}
		}
		
		return returnval;
	}
}