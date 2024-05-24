package com.ict.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AjaxController {
	
	@GetMapping("spring_ajax.do")
	public ModelAndView getSpringAjax() {
		return new ModelAndView("ajax/ajax_exam");
	}
	
	@GetMapping("spring_ajax2.do")
	public ModelAndView getSpringAjax2() {
		return new ModelAndView("ajax/ajax_exam2");
	}
	@GetMapping("spring_ajax2_1.do")
	public ModelAndView getSpringAjax2_1() {
		return new ModelAndView("ajax/ajax_exam2_1");
	}
	
	@GetMapping("data_go.do")
	public ModelAndView getData() {
		return new ModelAndView("ajax/ajax_exam3");
	}
	
	@GetMapping("data_go2.do")
	public ModelAndView getData2() {
		return new ModelAndView("ajax/ajax_exam4");
	}
}
