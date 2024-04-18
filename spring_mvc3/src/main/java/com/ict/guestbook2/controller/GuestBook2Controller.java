package com.ict.guestbook2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.guestbook2.dao.VO;
import com.ict.guestbook2.service.GuestBook2Service;

@Controller
public class GuestBook2Controller {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GuestBook2Service guestBook2Service;
	
	@GetMapping("gb2_list.do")
	public ModelAndView getGuestBook2List() {
		ModelAndView mv = new ModelAndView("guestbook2/list");
		List<VO> list = guestBook2Service.getGuestBook2List();
		if (list != null) {
			mv.addObject("list", list);
			return mv;
		}
		return new ModelAndView("guestbook2/error");
	}
	
	@GetMapping("gb2_write.do")
	public ModelAndView getGuestBook2Write() {
		return new ModelAndView("guestbook2/write");
	}
	
	@PostMapping("gb2_write_ok.do")
	public ModelAndView getGuestBook2WriteOK(VO vo, HttpSession session) {
		try {
			ModelAndView mv = new ModelAndView("redirect:gb2_list.do");
			
			String path = session.getServletContext().getRealPath("/resources/upload");
			
			MultipartFile file = vo.getFile();
			
			if (file.isEmpty()) {
				vo.setF_name("");
			}else {
				UUID uuid = UUID.randomUUID();
				String f_name = uuid + "_" + file.getOriginalFilename();
				vo.setF_name(f_name);
				
				byte[] in = file.getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			
			String pwd = passwordEncoder.encode(vo.getPwd());
			vo.setPwd(pwd);
			
			int result = guestBook2Service.getGuestBook2Insert(vo);
			if (result > 0) {
				return mv;
			}
			return new ModelAndView("guestbook2/error");
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("guestbook2/error");
	}

	@GetMapping("gb2_detail.do")
	public ModelAndView getGuestBook2Detail(String idx) {
		ModelAndView mv = new ModelAndView("guestbook2/detail");
		VO vo = guestBook2Service.getGuestBook2Detail(idx);
		if (vo != null) {
			mv.addObject("vo", vo);
			return mv;
		}
		return new ModelAndView("error");
	}

	@GetMapping("gb2_down.do")
	public void getGuestBook2Down(HttpServletRequest request, HttpServletResponse response) {
		try {
			String f_name = request.getParameter("f_name");
			String path = request.getSession().getServletContext()
					.getRealPath("/resources/upload/" + f_name);
			String r_path = URLEncoder.encode(path, "utf-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
			
			File file = new File(new String(path.getBytes(), "utf-8"));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(in, out);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@PostMapping("gb2_delete.do")
	public ModelAndView getGuestBook2Delete(String idx) {
		ModelAndView mv = new ModelAndView("guestbook2/delete");
		VO vo = guestBook2Service.getGuestBook2Detail(idx);
		mv.addObject("vo2", vo);
		return mv;
	}

	@PostMapping("gb2_delete_ok.do")
	public ModelAndView getGuestBook2DeleteOK(VO vo) {
		ModelAndView mv = new ModelAndView();
		String cpwd = vo.getPwd();
		
		VO vo2 = guestBook2Service.getGuestBook2Detail(vo.getIdx());
		String dpwd = vo2.getPwd();
		
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("guestbook2/delete");
			mv.addObject("pwdchk", "fail");
			mv.addObject("vo2", vo);
			return mv;
		}else {
			int result = guestBook2Service.getGuestBook2Delete(vo.getIdx());
			if (result > 0) {
				mv.setViewName("redirect:gb2_list.do");
				return mv;
			}
		}
		return new ModelAndView("guestbook2/error");
	}
	
	@PostMapping("gb2_update.do")
	public ModelAndView getGuestBook2Update(String idx) {
		ModelAndView mv = new ModelAndView("guestbook2/update");
		VO vo = guestBook2Service.getGuestBook2Detail(idx);
		mv.addObject("vo", vo);
		return mv;
	}
	
	@PostMapping("gb2_update_ok.do")
	public ModelAndView getGuestBook2UpdateOK(VO vo, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		String cpwd = vo.getPwd();
		
		VO vo2 = guestBook2Service.getGuestBook2Detail(vo.getIdx());
		String dpwd = vo2.getPwd();
		
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("guestbook2/update");
			mv.addObject("pwdchk", "fail");
			mv.addObject("vo", vo2);
			return mv;
		}else {
			try {
				String path = session.getServletContext().getRealPath("/resources/upload/");
				MultipartFile file = vo.getFile();
				if (file.isEmpty()) {
					vo.setF_name(vo.getOld_f_name());
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid + "_" + file.getOriginalFilename();
					vo.setF_name(f_name);
					
					byte[] in = file.getBytes();
					File out = new File(path, f_name);
					FileCopyUtils.copy(in, out);
				}
				int result = guestBook2Service.getGuestBook2Update(vo);
				if (result > 0) {
					return new ModelAndView("redirect:gb2_detail.do?idx=" + vo.getIdx());
				}
				return new ModelAndView("guestbook2/error");
			} catch (Exception e) {
				System.out.println(e);
			}
			return new ModelAndView("guestbook2/error");
		}
	}
}
















