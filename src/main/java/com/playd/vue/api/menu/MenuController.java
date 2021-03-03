package com.playd.vue.api.menu;


import com.playd.vue.common.JsonResponse;
import com.playd.vue.member.MemberModel;
import com.playd.vue.util.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping("/api/menu/list")
    public JsonResponse menuList(@RequestParam(required = false, defaultValue = "") String use_yn) {
        try {
            List<MenuModel> list = menuService.selectMenuList(use_yn);
            return new JsonResponse(true).setData(list);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }

    }

    @GetMapping("/api/menu/menuParentInfo")
    public JsonResponse menuParentInfo(@RequestParam(required = true, defaultValue = "0") int menu_no) {
        try {
            List<MenuModel> menuInfo = menuService.selectMenuParentInfo(menu_no);
            return new JsonResponse(true).setData(menuInfo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @GetMapping("/api/menu/menuInfo")
    public JsonResponse menuInfo(@RequestParam(required = true, defaultValue = "0") int menu_no) {
        try {
            MenuModel menuInfo = menuService.selectMenuInfo(menu_no);
            return new JsonResponse(true).setData(menuInfo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @PostMapping("/api/menu/menuSave")
    public JsonResponse menuSave(HttpServletRequest request, @RequestBody MenuModel menuModel ) {
        try {
            HttpSession session = request.getSession(false);
            if(session!=null) { // 사실 이럴 가능성은 없지만, 혹시 세션 끊겻을 경우
                MemberModel member = (MemberModel) session.getAttribute("loginInfo");
                menuModel.setReg_user_no(member.getUser_no());
            }





            if(!StringUtils.isEmpty(menuModel.getMenu_no()) && menuModel.getMenu_no()!=0) { //업데이트

                MenuModel originalMenu = menuService.selectMenuInfo(menuModel.getMenu_no());
                //만약 상위 메뉴가 변경 되었으면, seq 다시 딴다
                if(originalMenu.getP_menu_no() != menuModel.getP_menu_no()){
                    //max+1 seq 값을 가져온다.같은 부모 내에 순서를 정하기 위해
                    int seq = menuService.selectMenuSeqMax(menuModel.getP_menu_no());
                    //다른 모든 seq 값을 다 1씩 올려버린다
                    menuModel.setSeq(seq);
                    menuService.updateMenuSeqPlus(seq);
                }else{
                    menuModel.setSeq(originalMenu.getSeq());
                }

                //메뉴 정보 update
                menuService.updateMenuInfo(menuModel);
            } else { //신규
                //max+1 seq 값을 가져온다.같은 부모 내에 순서를 정하기 위해
                int seq = menuService.selectMenuSeqMax(menuModel.getP_menu_no());
                //다른 모든 seq 값을 다 1씩 올려버린다
                menuModel.setSeq(seq);
                menuService.updateMenuSeqPlus(seq);
                menuService.insertMenuInfo(menuModel);

                //끝나고 난후, 순서 seq가 뒤죽 박죽이 되는걸 방지하기 위해, 한번 더 seq를 차례로 넣자..이거 없으면 delete하는 순간에 엄청 띄엄띄엄 섞인다
                menuService.updateMenuSeq();
            }
            //권한 그룹 정보 delete 후 insert
            menuService.deleteGroupMenu(menuModel.getMenu_no());
            if(menuModel.getAuth_group()!=null) {
                List<MenuModel> authGroupList = menuModel.getAuth_group();
                for (MenuModel authGroup : authGroupList) {
                    authGroup.setMenu_no(menuModel.getMenu_no());
                    menuService.insertGroupMenu(authGroup);
                }
            }

            return new JsonResponse(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @PostMapping("/api/menu/menuDelete")
    public JsonResponse menuDelete(HttpServletRequest request, @RequestBody MenuModel menuModel ) {
        try {
            if(!StringUtils.isEmpty(menuModel.getMenu_no()) && menuModel.getMenu_no()!=0) {
                //권한 그룹 정보 delete - 외래키 때문에, 우선 메뉴 권한 부터
                menuService.deleteGroupMenu(menuModel.getMenu_no());
                //메뉴 정보 삭제
                menuService.deleteMenuInfo(menuModel);

                //끝나고 난후, 순서 seq가 뒤죽 박죽이 되는걸 방지하기 위해, 한번 더 seq를 차례로 넣자..이거 없으면 delete하는 순간에 엄청 띄엄띄엄 섞인다
                menuService.updateMenuSeq();

            }
            return new JsonResponse(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @GetMapping("/api/menu/menuGroupInfo")
    public JsonResponse menuGroupInfo(@RequestParam(defaultValue = "0") int menu_no) {
        try {
            List<MenuModel> menuGroupInfo = new ArrayList<>();
            if(menu_no!=0) {
                menuGroupInfo = menuService.selectMenuGroupInfo(menu_no);
            }
            List<MenuModel> allGroupInfo = menuService.selectAllGroupInfo();

            Map<String, List<MenuModel>> result = new HashMap<>();
            result.put("menuGroupInfo",menuGroupInfo);
            result.put("allGroupInfo",allGroupInfo);

            return new JsonResponse(true).setData(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @GetMapping("/api/menu/menuUrlDuplChk")
    public JsonResponse menuUrlDuplChk(MenuModel menuModel) {
        try {
            if(menuService.selectMenuUrlDuplChk(menuModel)>0){
                return new JsonResponse(false).setMessage("중복 된 URL 입니다.");
            }else{
                return new JsonResponse(true).setMessage("사용 가능 한 URL 입니다.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @GetMapping("/api/menu/menuPageIdDuplChk")
    public JsonResponse menuPageIdDuplChk(MenuModel menuModel) {
        try {
            if(menuService.selectMenuPageIdDuplChk(menuModel)>0){
                return new JsonResponse(false).setMessage("중복 된 메뉴ID 입니다.");
            }else{
                return new JsonResponse(true).setMessage("사용 가능 한 메뉴ID 입니다.");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }
    }

    @GetMapping("/api/menu/listTree")
    public JsonResponse menuTreeList(@RequestParam(required = false, defaultValue = "") String use_yn) {
        //트리 구조로 짜기 가져오기 위한 메소드
        try {
            List<MenuModel> list = menuService.selectMenuList(use_yn);
            JSONArray jsonList = new JSONArray();
            // 우선 첫번째 레벨 메뉴만
            for (int i = 0; i < list.size(); i++) {
                MenuModel menu = list.get(i);
                int p_menu_no = menu.getP_menu_no();
                int menu_no = menu.getMenu_no();
                if (p_menu_no == 0) {
                    boolean sub_menu_exist = false;
                    for (MenuModel sub_menu : list) {
                        int sub_p_menu_no = sub_menu.getP_menu_no();
                        if (menu_no == sub_p_menu_no) {
                            sub_menu_exist = true;
                            break;
                        }
                    }
                    JSONObject json_data = new JSONObject();
                    json_data.put("id", menu_no);
                    json_data.put("text", menu.getMenu_nm());
                    json_data.put("page_id", menu.getPage_id());
                    json_data.put("p_id", p_menu_no);
                    json_data.put("url", menu.getUrl());
                    json_data.put("use_yn", menu.getUse_yn());
                    json_data.put("opened", true); // 이상하게 이거 없으면 안된다.
                    json_data.put("loading", false); // 이상하게 이거 없으면 안된다.
                    if (sub_menu_exist) {
                        // 하위 메뉴들은 재귀함수로 돌림
                        json_data.put("children", makeSubMenuList(list, menu_no));
                    }
                    jsonList.add(json_data);
                }
            }
            return new JsonResponse(true).setData(jsonList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JsonResponse(false);
        }

    }

    public JSONArray makeSubMenuList(List<MenuModel> list, int menu_no) {
        JSONArray jsonList = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            MenuModel menu = list.get(i);
            int sub_menu_no = menu.getMenu_no();
            if (menu_no == menu.getP_menu_no()) {
                boolean sub_menu_exist = false;
                for (MenuModel sub_menu : list) {
                    int sub_p_menu_no = sub_menu.getP_menu_no();
                    if (sub_menu_no == sub_p_menu_no) {
                        sub_menu_exist = true;
                        break;
                    }
                }
                JSONObject json_data = new JSONObject();
                json_data.put("id", sub_menu_no);
                json_data.put("text", menu.getMenu_nm());
                json_data.put("page_id", menu.getPage_id());
                json_data.put("p_id", menu.getP_menu_no());
                json_data.put("url", menu.getUrl());
                json_data.put("use_yn", menu.getUse_yn());
                json_data.put("opened", true); // 이상하게 이거 없으면 안된다.
                json_data.put("loading", false); // 이상하게 이거 없으면 안된다.
                if (sub_menu_exist) {
                    // 하위 메뉴들은 재귀함수로 돌림
                    json_data.put("children", makeSubMenuList(list, sub_menu_no));
                }
                jsonList.add(json_data);
            }
        }
        return jsonList;
    }
}