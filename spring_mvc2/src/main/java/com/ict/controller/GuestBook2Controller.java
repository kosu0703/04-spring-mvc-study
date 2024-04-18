package com.ict.controller;

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

import com.ict.dao.VO;
import com.ict.service.GuestBook2Service;

@Controller
public class GuestBook2Controller {
	
	//	**암호화는 스프링 security 에서 지원하므로 pom.xml 에 추가해야한다.
	//	spring-security.xml 을 만들어서 bean 생성한 후
	//	web.xml 에서 지정해줘야 spring-security.xml 읽을 수 있도록 지정해야한다.
	//	읽어주면 객체생성이 되므로 오토와이어가 가능하게된다.
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
/*
	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/
	
	@Autowired
	private GuestBook2Service guestBook2Service;

	public void setGuestBook2Service(GuestBook2Service guestBook2Service) {
		this.guestBook2Service = guestBook2Service;
	}
	
	@GetMapping("gb2_list.do")
	public ModelAndView getGuestBook2List() {
		ModelAndView mv = new ModelAndView("list");
		
		List<VO> list = guestBook2Service.getGuestBook2List();
		
		if (list != null) {
			mv.addObject("list", list);
			return mv;
		}
		return new ModelAndView("error");
	}
	
	@GetMapping("gb2_write.do")
	public ModelAndView getGuestBook2Write() {
		return new ModelAndView("write");
	}
	
	@PostMapping("gb2_write_ok.do")
	public ModelAndView getGuestBook2WriteOK(VO vo, HttpSession session) {
		try {
			ModelAndView mv = new ModelAndView("redirect:gb2_list.do");
			
			String path = session.getServletContext().getRealPath("/resources/upload");
			
			//	**파일저장 
			//	현재 file 은 저장되어있지만 , f_name 은 저장되어있지 않다.
			//	DB 저장시 f_name 을 저장하는 처리를 해줘야한다.
			//	즉, 넘어온 파일의 정보 중 파일명을 f_name 에 넣어줘야 DB 에 저장할 수 있다.
			MultipartFile file = vo.getFile();
			if (file.isEmpty()) {
				vo.setF_name("");
			}else {
				//	오리지날 이름뿐이라 중복불가 (중복되면 업로드가 되지 않는다.)
				//	그러므로 파일이름을 UUID 를 이용해서 변경 후 DB 에 저장하자.
				UUID uuid = UUID.randomUUID();
				String f_name = uuid.toString() + "_" + file.getOriginalFilename();
				vo.setF_name(f_name);
				
				//	이미지 실제 저장(업로드)
				byte[] in = vo.getFile().getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			
			//	**비밀번호 암호화
			String pwd = passwordEncoder.encode(vo.getPwd());
			vo.setPwd(pwd);
			
			//	DB 저장
			int result = guestBook2Service.getGuestBook2Insert(vo);
			
			if (result > 0) {
				return mv;
			}
			return new ModelAndView("error");
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("error");
	}

	@GetMapping("gb2_detail.do")
	public ModelAndView getGuestBook2Detail(String idx) {
		ModelAndView mv = new ModelAndView("detail");
			
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
	public ModelAndView getGuestBook2Delete(@ModelAttribute("vo2") VO vo) {
		return new ModelAndView("delete");
	}

	@PostMapping("gb2_delete_ok.do")
	public ModelAndView getGuestBook2DeleteOK(VO vo) {
		ModelAndView mv = new ModelAndView();
		//	비밀번호가 맞는지 틀린지 검사하자 (DB에 있는 비밀번호는 암호화 되어있다.)
		
		//	jsp 에서 입력한 비밀번호
		String cpwd = vo.getPwd();
		
		//	DB 에서 암호화된 비밀번호
		VO vo2 = guestBook2Service.getGuestBook2Detail(vo.getIdx());
		String dpwd = vo2.getPwd();
		
		//	비밀번호를 비교해서 맞는지 틀린지 검사
		//	passwordEncoder.matches(암호화가 되지 않은것 , 암호화가 된것)
		//	일치하면 true , 아니면 false
		if (!passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("delete");
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
		return new ModelAndView("error");
	}
	
	@PostMapping("gb2_update.do")
	public ModelAndView getGuestBook2Update(String idx) {
		ModelAndView mv = new ModelAndView("update");
		
		VO vo = guestBook2Service.getGuestBook2Detail(idx);
		
		if (vo != null) {
			mv.addObject("vo", vo);
			return mv;
		}
		return new ModelAndView("error");
	}

	@PostMapping("gb2_update_ok.do")
	public ModelAndView getGuestBook2UpdateOK(VO vo, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		String cpwd = vo.getPwd();
		
		VO vo2 = guestBook2Service.getGuestBook2Detail(vo.getIdx());
		String dpwd = vo2.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("update");
			mv.addObject("pwdchk", "fail");
			mv.addObject("vo", vo2);
			return mv;
		}else {
			try {
				String path = session.getServletContext().getRealPath("/resources/upload/");
				MultipartFile file = vo.getFile();
				String old_f_name = vo.getOld_f_name();
				if (file.isEmpty()) {
					vo.setF_name(old_f_name);
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid.toString() + "_" + file.getOriginalFilename();
					vo.setF_name(f_name);
					
					//	이미지 실제 저장(업로드)
					byte[] in = vo.getFile().getBytes();
					File out = new File(path, f_name);
					FileCopyUtils.copy(in, out); 
				}
				
				//	실제 바뀐정보는 vo 
				int result = guestBook2Service.getGuestBook2Update(vo);
				
				if (result > 0) {
					mv.setViewName("redirect:gb2_detail.do?idx=" + vo.getIdx());
					return mv;
				}
				return new ModelAndView("error");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return new ModelAndView("error");
	}
	
	
}
