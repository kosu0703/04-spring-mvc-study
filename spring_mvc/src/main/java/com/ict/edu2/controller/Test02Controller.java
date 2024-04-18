package com.ict.edu2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu2.service.Test02Service;
import com.ict.edu2.vo.VO;

@Controller
public class Test02Controller {
	/*
	//방법1
	@PostMapping("calc.do")
	public ModelAndView calc(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("test02/result");
		
		String s1 = request.getParameter("s1");
		String s2 = request.getParameter("s2");
		String op = request.getParameter("op");
		String cPage = request.getParameter("cPage");
		
		//	**배열처리
		String[] hobby = request.getParameterValues("hobby");
		
		int su1 = Integer.parseInt(s1);
		int su2 = Integer.parseInt(s2);
		int result = test02Service.getCalc(su1, su2, op);
		
		mv.addObject("result", result);
		mv.addObject("s1", s1);
		mv.addObject("s2", s2);
		mv.addObject("op", op);
		mv.addObject("cPage", cPage);
		mv.addObject("hobby", hobby);
		
		return mv;
	}
	*/
	
	/*
	//방법2 (최소한 여기까지는)
	@Autowired
	private Test02Service test02Service;
	
	@PostMapping("calc.do")
	public ModelAndView calc(VO vo) {	//	**파라미터값을 VO 에 담는다.(자동으로 알아서)
		ModelAndView mv = new ModelAndView("test02/result");
		
		int su1 = Integer.parseInt(vo.getS1());
		int su2 = Integer.parseInt(vo.getS2());
		int result = test02Service.getCalc(su1, su2, vo.getOp());
		vo.setResult(String.valueOf(result));
		
		mv.addObject("vo", vo);
		
		return mv;
	}
	*/
	
	//방법3 (공부하기)
	@Autowired
	private Test02Service test02Service;
	
	@PostMapping("calc.do")
	public ModelAndView calc(@ModelAttribute("vo2")VO vo) {	//	**model 에 vo 를 저장해준다
		ModelAndView mv = new ModelAndView("test02/result");
		
		int su1 = Integer.parseInt(vo.getS1());
		int su2 = Integer.parseInt(vo.getS2());
		int result = test02Service.getCalc(su1, su2, vo.getOp());
		vo.setResult(String.valueOf(result));
		
		//mv.addObject("vo2", vo);
		//	이거 대신에 @ModelAttribute("vo2") 사용

		return mv;
	}
	
}
