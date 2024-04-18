package com.ict.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ict.guestbook.service.GuestBookDAO;
import com.ict.guestbook.service.GuestBookVO;

@Controller
public class GuestBookController {
	//	dao 를 사용하기 위해 오토와이어
	//	root-context.xml 의 id 와 변수이름이 같아야한다
	@Autowired
	private GuestBookDAO guestBookDAO;
	
	//	button 으로 자바스크립트로 오므로 get 맵핑해주자
	@GetMapping("guestbook_list.do")
	public ModelAndView guestBookList() {
		ModelAndView mv = new ModelAndView("guestbook/list");
		
		List<GuestBookVO> list = guestBookDAO.getList();
		
		mv.addObject("list", list);
		
		return mv;
	}
	
	@GetMapping("write_go.do")
	public ModelAndView writeGo() {
		//ModelAndView mv = new ModelAndView("guestbook/write");
		//	어차피 하는일이 없기때문에 바로 리턴해준다
		return new ModelAndView("guestbook/write");
	}
	
	@PostMapping("write_ok.do")
	public ModelAndView writeOk(GuestBookVO vo) {
		//	redirect 를 만나면 클라이언트에 갔다가 다시 돌아오면서 
		//	guestbook_go.do 를 가지고 온다.
		//	그래서 바로 guestBookList() 메서드로 간다.
		ModelAndView mv = new ModelAndView("redirect:guestbook_list.do");
		
		int result = guestBookDAO.getInsert(vo);
		
		if (result > 0) {
			//mv = guestBookList();
			return mv; 
		}
		return null;
	}
	
	@GetMapping("detail_go.do")
	public ModelAndView detail(@RequestParam("idx") String idx) {
		ModelAndView mv = new ModelAndView("guestbook/detail");
		
		GuestBookVO vo = guestBookDAO.getDetail(idx);
		
		if (vo != null) {
			mv.addObject("vo", vo);
			return mv;
		}
		return null;
	}
	
	//	vo 로 받아서 
	@PostMapping("delete.do")
	public ModelAndView delete(@ModelAttribute("gvo") GuestBookVO vo) {
		return new ModelAndView("guestbook/delete");
	}
	
	@PostMapping("delete_ok.do")
	public ModelAndView deleteOk(GuestBookVO vo) {
		ModelAndView mv = new ModelAndView("redirect:guestbook_list.do");
		
		int result = guestBookDAO.getDelete(vo.getIdx());
		
		if (result > 0) {
			return mv;
		}
		return null;
	}
	
	@PostMapping("update.do")
	public ModelAndView update(@RequestParam("idx") String idx) {
		ModelAndView mv = new ModelAndView("guestbook/update");
		
		GuestBookVO vo = guestBookDAO.getDetail(idx);
		if (vo != null) {
			mv.addObject("vo", vo);
			return mv;
		}
		return null;
	}
	
	@PostMapping("update_ok.do")
	public ModelAndView updateOk(GuestBookVO vo) {
		ModelAndView mv = new ModelAndView("redirect:detail_go.do?idx=" + vo.getIdx());
		
		int result = guestBookDAO.getUpdate(vo);
		
		if (result > 0) {
			return mv;
		}
		return null;
	}
	
	
}
