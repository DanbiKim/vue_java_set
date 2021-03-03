package com.playd.vue.member;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {
    MemberModel selectLoginProcess(MemberModel member);
    MemberModel selectAutoLoginProcess(String user_no);
    void updateLoginFailCnt(MemberModel member);
    void updateLoginFailCntReset(MemberModel member);
    void updateLastLoginTime(MemberModel member);
    void insertLoginHistory(MemberModel member);
}
