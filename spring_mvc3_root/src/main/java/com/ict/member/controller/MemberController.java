package com.ict.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.member.dao.MemberVO;
import com.ict.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("login.do")
	public ModelAndView getLogin() {
		return new ModelAndView("member/login");
	}
	
	@PostMapping("login_ok.do")
	public ModelAndView getLoginOK(MemberVO mvo, HttpSession session) {
		try {
			ModelAndView mv = new ModelAndView();
			
			MemberVO mvo2 = memberService.getLogin(mvo);
			
			if (mvo2 == null) {
				session.setAttribute("loginChk", "fail");
				mv.setViewName("member/login");
				return mv;
			}else {
				session.setAttribute("loginChk", "ok");
				//	admin 으로 성공시
				if (mvo2.getM_id().equals("admin")) {
					session.setAttribute("admin", "ok");
				}
				//	DB 갔다와서 성공한 건 mvo2 이므로
				session.setAttribute("mvo", mvo2);
				mv.setViewName("redirect:shop_list.do");
				return mv;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("member/error");
	}
	
	@GetMapping("logout.do")
	public ModelAndView getLogout(HttpSession session) {
		//	세션 전체 초기화
		//session.invalidate();
		
		//	특정한 정보만 삭제
		session.removeAttribute("loginChk");
		session.removeAttribute("admin");
		session.removeAttribute("mvo");
		return new ModelAndView("redirect:shop_list.do");
	}
	
	
}









