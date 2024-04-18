package com.ict.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu.service.Test01Service;

@Controller
public class Test01Controller {
	
	//	어노테이션에 의해 만들어진 서비스를 사용할때는 @Autowired 사용
	//	(변수이름과 참조하는 클래스의 id 가 같아야한다.)
	@Autowired
	//	@Qualifier("service") => 변수이름 다르게 할때 사용
	//	**기본적으로 이름 같게 사용하자
	private Test01Service test01Service;
	
	@GetMapping("hello.do")
	public ModelAndView HelloCommand(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("test01/result");
		
		String msg = test01Service.getGreeting();
		
		mv.addObject("msg", msg);
		
		return mv;
	}
	
}
