package com.ict.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.emp.dao.EmpVO;
import com.ict.emp.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	@PostMapping("emp_list.do")
	public ModelAndView getEmpList() {
		try {
			ModelAndView mv = new ModelAndView("emp/emp_list");
			List<EmpVO> emp_list = empService.getEmpList();
			if (emp_list != null) {
				mv.addObject("emp_list", emp_list);
				return mv;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("emp/error");
	}
	
	@PostMapping("emp_search.do")
	public ModelAndView getEmpSearchList(@ModelAttribute("idx")String idx, String keyword) {
		try {
			ModelAndView mv = new ModelAndView("emp/search_list");
			List<EmpVO> search_list = empService.getEmpSearchList(idx, keyword);
			if (search_list != null) {
				mv.addObject("search_list", search_list);
				return mv;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("emp/error");
	}
	
	
	
	
}














