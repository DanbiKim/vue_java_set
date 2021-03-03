package com.playd.vue.common;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	void insertWhatever(HashMap<String,Object> paramMap);
	List<HashMap<String,Object>> selectCommCd(HashMap<String, Object> paramBody);
	
	int selectExistTable(SearchModel searchModel);
	List<HashMap<String, Object>> selectTable(SearchModel searchModel);
}