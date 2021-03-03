package com.playd.vue.member;


import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberMapper memberMapper;

    public MemberModel selectLoginProcess(MemberModel member){
        return memberMapper.selectLoginProcess(member);
    }

    public MemberModel selectAutoLoginProcess(String user_no){
        return memberMapper.selectAutoLoginProcess(user_no);
    }

    public void updateLoginFailCnt(MemberModel member){
        memberMapper.updateLoginFailCnt(member);
    }

    public void updateLoginFailCntReset(MemberModel member){
        memberMapper.updateLoginFailCntReset(member);
    }

    public void updateLastLoginTime(MemberModel member){
        memberMapper.updateLastLoginTime(member);
    }

    public void insertLoginHistory(MemberModel member){
        memberMapper.insertLoginHistory(member);
    }

}
