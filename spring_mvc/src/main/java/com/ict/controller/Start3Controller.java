package com.ict.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Start3Controller {
	//	실제 실행 메서드
	//	메서드 위에 요청이 오면 실행해야되므로 요청 표시를 해줘야한다.
/*
	//	옛날방식
	@RequestMapping(value = "start3.do", method = RequestMethod.GET)
	@RequestMapping(value = "start3.do", method = RequestMethod.POST)
	//	잘모를때
	@RequestMapping("start3.do") => get 인지 post 인지 모를때
	//	최근
	@GetMapping("start3.do")
	@PostMapping("start3.do")
*/
	
	//	a 링크는 get 방식이므로 @GetMapping 사용
	@GetMapping("/start3.do")
	public ModelAndView exec(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("result3");
		mv.addObject("city", "서울");
		return mv;
	}
	
}
