package com.playd.vue.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.playd.vue.util.StringUtil;


@RestController
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@GetMapping("error/{errorCode}")
	public JsonResponse errorProcess(@PathVariable(value = "errorCode") String errorCode) {
		try {
			if(errorCode!=null) {
				String message = "";
				if(errorCode.equals("401")) { //error 코드에 따라 메세지 분기 시킨다
					message="인증이 실패하였습니다.";
				}else if(errorCode.equals("500")){
					message="500 에러";
				}else if(errorCode.equals("401-1")){
					message="허용되지 않은 IP 입니다.";
					errorCode="401";
				}
				
				return new JsonResponse(false).setMessage(message).setCode(errorCode);
			}else {
				return new JsonResponse(false).setMessage("알수 없는 오류 ");
			}
		}catch(Exception e) {
			return new JsonResponse(false).setMessage("알수 없는 오류 : "+e.getMessage());
		}
		
	}
	
	

	//데이터 삽입
	@PostMapping("common/{any_table_nm}")
	public JsonResponse insertProcess(@RequestBody HashMap<String, Object> paramBody, @PathVariable(value = "any_table_nm") String any_table_nm) throws Exception{
		try {
			if(!commonService.chkExceptTable(any_table_nm)) {
				return new JsonResponse(false).setMessage("삽입이 불가능한 테이블 입니다.");
			}
			
			if(!StringUtil.isNull(any_table_nm)) {
				paramBody.put("any_table_nm", any_table_nm);
				
				commonService.insertWhatever(paramBody);
				
				return new JsonResponse(true);
			}else {
				return new JsonResponse(false).setMessage("테이블이 정의 되지 않았습니다.");
			}
		}catch(Exception e) {
			return new JsonResponse(false).setMessage("알수 없는 오류 : "+e.getMessage());
		}
		
	}
	
	//데이터 조회
	@GetMapping("common/{any_table_nm}")
	public JsonResponse selectTable(SearchModel searchModel, @PathVariable(value = "any_table_nm") String any_table_nm) throws Exception{
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		try {
			
			if(!commonService.chkExceptTable(any_table_nm)) {
				return new JsonResponse(false).setMessage("검색이 불가능한 테이블 입니다.");
			}
			if(!StringUtil.isNull(any_table_nm)) {
				searchModel.setSearch_tb(any_table_nm);
			}
			if(commonService.selectExistTable(searchModel)) {
				resultList = commonService.selectTable(searchModel);
				
				return new JsonResponse(true).setData(resultList);
			} else {
				return new JsonResponse(false).setMessage("존재하지 않는 검색시도입니다.");
			}
		} catch (Exception ex) {
			return new JsonResponse(false);
		}
		
	}
	
	
	
	
}
