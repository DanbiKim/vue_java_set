package com.playd.vue.member;

import lombok.*;

@Data
public class MemberModel {
    private int user_no;
    private String user_nm;
    private String user_id;
    private String user_pw;
    private String email;
    private String phone;
    private String dept_no;
    private String emp_id;
    private String contract_type;
    private String use_yn;
    private String login_date;
    private int reg_user_no;
    private String reg_date;
    private String update_date;
    private String update_yn;
    private String del_yn;
    private String del_date;
    private int del_user_no;
    private String retire_date;
    private String system_yn;
    private int login_fail_cnt;
    private String login_fail_lock;
    private String login_fail_yn;
    private String last_change_passwd_date;
    private String reason;
    private String ip;
}
