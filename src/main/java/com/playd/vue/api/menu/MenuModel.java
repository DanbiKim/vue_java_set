package com.playd.vue.api.menu;

import lombok.*;

import java.util.List;

@Data
public class MenuModel {
    private int menu_no;
    private String menu_nm;
    private String page_id;
    private int p_menu_no;
    private String p_menu_nm;
    private String target;
    private String url;
    private String memo;
    private int seq;
    private int reg_user_no;
    private String reg_date;
    private String use_yn;

    private int group_no;
    private String group_nm;

    private int id;
    private String name;

    private List<MenuModel> auth_group;
}
