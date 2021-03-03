package com.playd.vue.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
	int selectServerCheck();
}
