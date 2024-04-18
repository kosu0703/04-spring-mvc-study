package com.ict.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.guestbook.dao.GuestBookVO;
import com.ict.guestbook.service.GuestBookService;

@Controller
public class GuestBookController {

	@Autowired
	private GuestBookService guestBookService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("gb_list.do")
	public ModelAndView getGuestBookList() {
		ModelAndView mv = new ModelAndView("guestbook/list");
		List<GuestBookVO> list = guestBookService.getGuestBookList();
		if (list != null) {
			mv.addObject("list", list);
			return mv;
		}
		return null;
	}
	
	@GetMapping("gb_write.do")
	public ModelAndView getGuestBookWrite() {
		return new ModelAndView("guestbook/write");
	}
	
	@PostMapping("gb_write_ok.do")
	public ModelAndView getGuestBookWriteOK(GuestBookVO vo) {
		ModelAndView mv = new ModelAndView("redirect:gb_list.do");
		
		String pwd = passwordEncoder.encode(vo.getPwd());
		vo.setPwd(pwd);
		
		int result = guestBookService.getGuestBookInsert(vo);
		if (result > 0) {
			return mv;
		}
		return null;
	}
	
	@GetMapping("gb_detail.do")
	public ModelAndView getGuestBookDetail(String idx) {
		ModelAndView mv = new ModelAndView("guestbook/detail");
		
		GuestBookVO vo = guestBookService.getGuestBookDetail(idx);
		if (vo != null) {
			mv.addObject("vo", vo);
			return mv;
		}
		return null;
	}
	
	@PostMapping("gb_delete.do")
	public ModelAndView getGuestBookDelete(@ModelAttribute("idx") String idx) {
		return new ModelAndView("guestbook/delete");
	}

	@PostMapping("gb_delete_ok.do")
	public ModelAndView getGuestBookDeleteOK(GuestBookVO vo) {
		ModelAndView mv = new ModelAndView();
		String cpwd = vo.getPwd();
		GuestBookVO vo2 = guestBookService.getGuestBookDetail(vo.getIdx());
		String dpwd = vo2.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("guestbook/delete");
			mv.addObject("pwdchk", "fail");
			mv.addObject("idx", vo2.getIdx());
			return mv;
		} else {
			int result = guestBookService.getGuestBookDelete(vo.getIdx());
			if (result > 0) {
				mv.setViewName("redirect:gb_list.do");
				return mv;
			}
			return null;
		}
	}

	@PostMapping("gb_update.do")
	public ModelAndView getGuestBookUpdate(String idx) {
		ModelAndView mv = new ModelAndView("guestbook/update");
		GuestBookVO vo = guestBookService.getGuestBookDetail(idx);
		mv.addObject("vo", vo);
		return mv;
	}

	@PostMapping("gb_update_ok.do")
	public ModelAndView getGuestBookUpdateOK(GuestBookVO vo) {
		ModelAndView mv = new ModelAndView();
		String cpwd = vo.getPwd();
		GuestBookVO vo2 = guestBookService.getGuestBookDetail(vo.getIdx());
		String dpwd = vo2.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("guestbook/update");
			mv.addObject("pwdchk", "fail");
			//	수정 전 내용을 되돌려주려면 : vo2
			//	수정 후 내용을 되돌려주려면 : vo
			mv.addObject("vo", vo);
			return mv;
		} else {
			int result = guestBookService.getGuestBookUpdate(vo);
			if (result > 0) {
				mv.setViewName("redirect:gb_detail.do?idx=" + vo.getIdx());
				return mv;
			}
			return null;
		}
	}
	
	
	
	
	
	
	
}
