package com.ict.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//	홈컨트롤러와 비슷하게
@Controller
public class Start4Controller {
	//	컨트롤러 메서드의 반환형은 String, ModelAndView 
	@GetMapping("start4.do")
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 	ModelAndView 안됨 => 왜 안되지?
		/*
		ModelAndView mv = new ModelAndView();
		mv.addObject("computer", "14세대 i7");
		*/
		
		request.setAttribute("computer", "14세대 i7");
		return "result4";
	}
}
