package com.playd.vue.member;


import com.playd.vue.common.JsonResponse;
import com.playd.vue.util.StringUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;

@Controller
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    // 회원가입 페이지
  /*  @GetMapping("/user/signup")
    public String dispSignup() {
        return "login/signup";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MemberModel memberModel) {
       // memberService.joinUser(memberModel);

        return "redirect:/user/login?s=joinSuccess";
    }
*/
    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginPage(HttpServletRequest request, Model model) {
        String rUrl = StringUtil.NVL(request.getParameter("rUrl"),"/");
        model.addAttribute("rUrl", rUrl);
        return "login/login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public JsonResponse loginProcess(HttpServletRequest request, HttpServletResponse response, MemberModel login) {
        try {
            String client_ip = StringUtil.getRemoteIp(request);

            login.setIp(client_ip);

            MemberModel member = memberService.selectLoginProcess(login);
            if(member==null) {
                //혹시 이전에 쌓인 쿠키가 있을수 있어서 삭제
                delLoginInfo(request,response);

                //로그인 실패 횟수 증가
                memberService.updateLoginFailCnt(login);

                int fail_cnt = login.getLogin_fail_cnt();

                memberService.insertLoginHistory(login);

                if(fail_cnt == -1) {
                    login.setReason("ID/PW를 확인해주세요");
                    login.setLogin_fail_yn("n");
                    return new JsonResponse(false).setMessage("ID/PW를 확인해주세요");
                }else if(fail_cnt >0 && fail_cnt <=5){
                    login.setReason("ID/PW를 확인해주세요("+fail_cnt+"/5)");
                    login.setLogin_fail_yn("n");
                    return new JsonResponse(false).setMessage("ID/PW를 확인해주세요("+fail_cnt+"/5)");
                }else {
                    login.setReason("비밀번호를 5번 이상 틀리셨습니다. 비밀번호 찾기로 다시 설정해주세요");
                    login.setLogin_fail_yn("n");
                    return new JsonResponse(false).setMessage("비밀번호를 5번 이상 틀리셨습니다. 비밀번호 찾기로 다시 설정해주세요");
                }
            }else {
                if (member.getLogin_fail_cnt() >= 5) {
                    login.setReason("비밀번호를 5번 이상 틀리셨습니다. 비밀번호 찾기로 다시 설정해주세요");
                    login.setLogin_fail_yn("n");
                    memberService.insertLoginHistory(login);
                    return new JsonResponse(false).setMessage("비밀번호를 5번 이상 틀리셨습니다. 비밀번호 찾기로 다시 설정해주세요");
                }

                if (member.getDel_yn().equals("y")) {
                    login.setReason("탈퇴 처리 된 계정입니다.");
                    login.setLogin_fail_yn("n");
                    memberService.insertLoginHistory(login);
                    return new JsonResponse(false).setMessage("탈퇴 처리 된 계정입니다.");
                } else {
                    //로그인 history 기록
                    login.setLogin_fail_yn("y");
                    memberService.insertLoginHistory(login);

                    //비밀번호 실패 카운트 초기화
                    memberService.updateLoginFailCntReset(login);

                    //로그인 성공
                    setLoginInfo(request, response, member); //로그인 쿠키 생성

                    //마지막 로그인 업데이트
                    memberService.updateLastLoginTime(member);
                }
            }

            return new JsonResponse(true);
        }catch (Exception e){
            return new JsonResponse(false).setMessage(e.getMessage());
        }
    }

    public void setLoginInfo(HttpServletRequest request, HttpServletResponse response, MemberModel loginInfo) throws Exception{
        HttpSession session = request.getSession(false);
        if(session!=null) {
            session.invalidate();//초기화
        }
        session=request.getSession(true);
        session.setMaxInactiveInterval(60*60*3); //3시간
        session.setAttribute("loginInfo", loginInfo);

        StringUtil.setCookie(response, "user_no", StringUtil.encrypt(String.valueOf(loginInfo.getUser_no())), 60*60*3);
        StringUtil.setCookie(response, "user_id", StringUtil.encrypt(loginInfo.getUser_id()), 60*60*3);
        StringUtil.setCookie(response, "user_nm", StringUtil.encrypt(loginInfo.getUser_nm()), 60*60*3);
    }

    private void delLoginInfo(HttpServletRequest request, HttpServletResponse response){
        String user_no = StringUtil.getCookie(request,"user_no");
        HttpSession session = request.getSession(false);
        if(session!=null) {
            session.invalidate();//초기화
        }
        StringUtil.delCookie(response,"user_no");
        StringUtil.delCookie(response,"user_nm");
        StringUtil.delCookie(response,"user_id");
    }

    public boolean autoLoginInfo(HttpServletResponse response, HttpServletRequest request, String user_no)throws Exception {
        MemberModel loginInfo = memberService.selectAutoLoginProcess(user_no);
        if(loginInfo!=null){
            setLoginInfo(request,response,loginInfo);
            return true;
        }else{
            return false;
        }

    }

    // 로그아웃
    @GetMapping("/user/logout")
    public void logout(HttpServletResponse response,HttpServletRequest request) throws Exception{
        delLoginInfo(request,response);
        response.sendRedirect("/");
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "login/myinfo";
    }

    @GetMapping("/user/loginChk")
    @ResponseBody
    public JsonResponse loginChk(HttpServletRequest request) throws Exception{
        try {
            HttpSession session = request.getSession(false);
            String user_no=StringUtil.getCookie(request, "user_no");
            MemberModel member = null;
            if(session!=null && user_no !=null && !user_no.equals("")) {
                member= (MemberModel)session.getAttribute("loginInfo");

                String de_user_no = StringUtil.decrypt(user_no);

                if(member.getUser_no() == Integer.parseInt(de_user_no)) {
                    return new JsonResponse(true);
                }else {
                    return new JsonResponse(false).setMessage("session no && cookie no is other");
                }
            }else {
                return new JsonResponse(false).setMessage("not Authentication");
            }
        }catch (Exception e){
            return new JsonResponse(false).setMessage(e.getMessage());
        }
    }

}