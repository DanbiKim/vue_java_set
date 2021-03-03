package com.playd.vue.api.jjy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.playd.vue.common.JsonResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * JjyController
 */
@RestController
public class JjyController {
    private static final Logger logger = LoggerFactory.getLogger(JjyController.class);

    @Autowired
    private JjyService jjyService;
    
    @GetMapping("/api/jjy/board/{page_no}")
	public JsonResponse getBoardList(HttpServletRequest request, @PathVariable(value = "page_no")  int page_no) throws Exception{
		System.out.println("!!");
		try {
			if(page_no != 0){
				JjyModel jjyModel = new JjyModel();
				jjyModel.setPage_no(page_no);				
				
				List<JjyModel> resultList = jjyService.selectBoardList(jjyModel);

				return new JsonResponse(true).setData(resultList);
			} else {
				return new JsonResponse(false).setMessage("파라미터 부족(required : page_no)").setCode("500");
			}
					
		} catch (Exception ex) {
			return new JsonResponse(false);
		}
		
	}
}