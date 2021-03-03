package com.playd.vue.api.menu;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuMapper menuMapper;

    public List<MenuModel> selectMenuList(String use_yn){
        return menuMapper.selectMenuList(use_yn);
    }

    public List<MenuModel> selectMenuParentInfo(int menu_no){
        return menuMapper.selectMenuParentInfo(menu_no);
    }

    public MenuModel selectMenuInfo(int menu_no){
        return menuMapper.selectMenuInfo(menu_no);
    }

    public List<MenuModel> selectMenuGroupInfo(int menu_no){
        return menuMapper.selectMenuGroupInfo(menu_no);
    }

    public int selectMenuUrlDuplChk(MenuModel menuModel){
        return menuMapper.selectMenuUrlDuplChk(menuModel);
    }

    public int selectMenuPageIdDuplChk(MenuModel menuModel){
        return menuMapper.selectMenuPageIdDuplChk(menuModel);
    }

    public List<MenuModel> selectAllGroupInfo(){
        return menuMapper.selectAllGroupInfo();
    }

    public void insertMenuInfo(MenuModel menuModel) {
        menuMapper.insertMenuInfo(menuModel);
    }

    public void updateMenuSeq(){
        menuMapper.updateMenuSeq();
    }

    public void updateMenuSeqPlus(int seq){
        menuMapper.updateMenuSeqPlus(seq);
    }

    public int selectMenuSeqMax(int p_menu_no) {
        return menuMapper.selectMenuSeqMax(p_menu_no);
    }

    public void deleteMenuInfo(MenuModel menuModel) {
        menuMapper.deleteMenuInfo(menuModel);
    }

    public void updateMenuInfo(MenuModel menuModel) {
        menuMapper.updateMenuInfo(menuModel);
    }

    public void deleteGroupMenu(int menu_no){
        menuMapper.deleteGroupMenu(menu_no);
    }

    public void insertGroupMenu(MenuModel menuModel){
        menuMapper.insertGroupMenu(menuModel);
    }
}
