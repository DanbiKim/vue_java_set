package com.playd.vue.api.jjy;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JjyMapper {
	
	List<JjyModel> selectBoardList(JjyModel jjyModel);

}