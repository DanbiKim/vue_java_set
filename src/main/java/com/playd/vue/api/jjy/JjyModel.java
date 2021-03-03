package com.playd.vue.api.jjy;

import lombok.Data;

@Data
public class JjyModel {
	
	private int idx;
	private String title;
	private String contents;
	private int read_cnt;
	private int page_no;
	private String reg_ymdt; // 등록일시
	private String mod_ymdt; // 수정일시
}
