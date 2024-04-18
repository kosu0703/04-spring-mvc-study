package com.ict.bbs.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.bbs.dao.BbsVO;
import com.ict.bbs.dao.CommentVO;
import com.ict.bbs.service.BbsService;
import com.ict.common.Paging;

@Controller
public class BbsController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private BbsService bbsService;
	
	@Autowired
	private Paging paging;

	@RequestMapping("bbs_list.do")
	public ModelAndView getBbsList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("bbs/list");
		
		//	전체 게시물의 수를 구하자
		int count = bbsService.getTotal();
		paging.setTotalRecord(count);
		
		//	전체페이지 수를 구하자
		if (paging.getTotalRecord() <= paging.getNumPerPage()) {
			paging.setTotalPage(1);
		}else {
			paging.setTotalPage(paging.getTotalRecord() / paging.getNumPerPage());
			if (paging.getTotalRecord() % paging.getNumPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage() + 1);
			}
		}
		
		//	현재페이지 구하자
		String cPage = request.getParameter("cPage");
		if (cPage == null) {
			paging.setNowPage(1);
		}else {
			paging.setNowPage(Integer.parseInt(cPage));
		}
		
		//	begin 과 end 를 구하자
		
		//	시작블럭과 끝블럭을 구하자
		
		
		List<BbsVO> list = bbsService.getBbsList();
		if (list != null) {
			mv.addObject("list", list);
			return mv;
		}
		return new ModelAndView("bbs/error");
	}
	
	@GetMapping("bbs_write.do")
	public ModelAndView getBbsWrite() {
		return new ModelAndView("bbs/write");
	}
	
	@PostMapping("bbs_write_ok.do")
	public ModelAndView getBbsWriteOK(BbsVO bvo, HttpSession session) {
		try {
			ModelAndView mv = new ModelAndView("redirect:bbs_list.do");
			
			String path = session.getServletContext().getRealPath("/resources/upload");
			MultipartFile file = bvo.getFile();
			if (file.isEmpty()) {
				bvo.setF_name("");
			}else {
				UUID uuid = UUID.randomUUID();
				String f_name = uuid + "_" + file.getOriginalFilename();
				bvo.setF_name(f_name);
				
				byte[] in = file.getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			
			String pwd = passwordEncoder.encode(bvo.getPwd());
			bvo.setPwd(pwd);
			
			int result = bbsService.getBbsInsert(bvo);
			if (result > 0) {
				return mv;
			}
			return new ModelAndView("bbs/error");
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("bbs/error");
	}
	
	@GetMapping("bbs_detail.do")
	public ModelAndView getBbsDetail(String b_idx) {
		ModelAndView mv = new ModelAndView("bbs/detail");
		
		//	조회수 증가
		int result = bbsService.getHitUpdate(b_idx);
		//	상세보기
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		if (result > 0 && bvo != null) {
			//	댓글 가져오기
			List<CommentVO> comm_list = bbsService.getCommList(b_idx);
			mv.addObject("comm_list", comm_list);
			mv.addObject("bvo", bvo);
			return mv;
		}
		return new ModelAndView("bbs/error");
	}
	
	@PostMapping("comm_write.do")
	public ModelAndView getCommWrite(CommentVO cvo) {
		ModelAndView mv = new ModelAndView("redirect:bbs_detail.do?b_idx=" + cvo.getB_idx());
		int result = bbsService.getCommInsert(cvo);
		if (result > 0) {
			return mv;
		}
		return new ModelAndView("bbs/error");
	}
	
	@PostMapping("comm_delete.do")
	public ModelAndView getCommDelete(CommentVO cvo) {
		ModelAndView mv = new ModelAndView("redirect:bbs_detail.do?b_idx=" + cvo.getB_idx());
		int result = bbsService.getCommDelete(cvo.getC_idx());
		if (result > 0) {
			return mv;
		}
		return new ModelAndView("bbs/error");
	}
	
	@GetMapping("bbs_down.do")
	public void getDown(HttpServletRequest request, HttpServletResponse response) {
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
	
	@PostMapping("bbs_delete.do")
	public ModelAndView getBbsDelete(@ModelAttribute("b_idx")String b_idx) {
		return new ModelAndView("bbs/delete");
	}
	
	@PostMapping("bbs_delete_ok.do")
	public ModelAndView getBbsDeleteOK(@ModelAttribute("b_idx")String b_idx, 
			String pwd) {
		ModelAndView mv = new ModelAndView();
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		String dpwd = bvo.getPwd();
		if (! passwordEncoder.matches(pwd, dpwd)) {
			mv.setViewName("bbs/delete");
			mv.addObject("pwdchk", "fail");
			return mv;
		}else {
			int result = bbsService.getBbsDelete(b_idx);
			if (result > 0) {
				mv.setViewName("redirect:bbs_list.do");
				return mv;
			}
		}
		return new ModelAndView("bbs/error");
	}
	
	@PostMapping("bbs_update.do")
	public ModelAndView getBbsUpdate(String b_idx) {
		ModelAndView mv = new ModelAndView("bbs/update");
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		mv.addObject("bvo", bvo);
		return mv;
	}
	
	@PostMapping("bbs_update_ok.do")
	public ModelAndView getBbsUpdateOK(BbsVO bvo, @ModelAttribute("b_idx")String b_idx,
			HttpSession session) {
		ModelAndView mv = new ModelAndView();
		BbsVO bvo2 = bbsService.getBbsDetail(b_idx);
		String dpwd = bvo2.getPwd();
		if (! passwordEncoder.matches(bvo.getPwd(), dpwd)) {
			mv.setViewName("bbs/update");
			mv.addObject("pwdchk", "fail");
			//bvo.setF_name(bvo.getOld_f_name());
			bvo.setF_name(bvo2.getF_name());
			mv.addObject("bvo", bvo);
			return mv;
		}else {
			try {
				String path = session.getServletContext().getRealPath("/resources/upload");
				MultipartFile file = bvo.getFile();
				if (file.isEmpty()) {
					bvo.setF_name(bvo.getOld_f_name());
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid + "_" + file.getOriginalFilename();
					bvo.setF_name(f_name);
				}
				int result = bbsService.getBbsUpdate(bvo);
				if (result > 0) {
					mv.setViewName("redirect:bbs_detail.do");
					return mv;
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return new ModelAndView("bbs/error");
	}
	
}












