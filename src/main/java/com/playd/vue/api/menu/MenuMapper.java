package com.playd.vue.api.menu;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuMapper {
    List<MenuModel> selectMenuList(String use_yn);
    List<MenuModel> selectMenuParentInfo(int menu_no);
    MenuModel selectMenuInfo(int menu_no);
    List<MenuModel> selectMenuGroupInfo(int menu_no);
    List<MenuModel> selectAllGroupInfo();
    int selectMenuUrlDuplChk(MenuModel menuModel);
    int selectMenuPageIdDuplChk(MenuModel menuModel);
    void insertMenuInfo(MenuModel menuModel);
    void updateMenuSeq();
    void updateMenuSeqPlus(int seq);
    int selectMenuSeqMax(int p_menu_no);
    void deleteMenuInfo(MenuModel menuModel);
    void updateMenuInfo(MenuModel menuModel);
    void deleteGroupMenu(int menu_no);
    void insertGroupMenu(MenuModel menuModel);
}
