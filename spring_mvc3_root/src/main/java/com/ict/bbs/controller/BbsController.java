package com.ict.bbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.Buffer;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.bbs.dao.BbsVO;
import com.ict.bbs.dao.CommentVO;
import com.ict.bbs.service.BbsService;
import com.ict.common.Paging;

@Controller
public class BbsController {
	
	@Autowired
	private BbsService bbsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Paging paging;
	
	@RequestMapping("bbs_list.do")
	//	1. 페이징 기법을 하기위해서 제일 먼저 파라미터 값을 받는다.
	public ModelAndView getBbsList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("bbs/list");
		
		/* 페이징 기법이전
		List<BbsVO> list = bbsService.getBbsList();
		if (list != null) {
			mv.addObject("list", list);
			return mv;
		}
		return new ModelAndView("bbs/error");
		 */
		
		//	2. 전체 게시물의 수를 구해서 넣어주자
		int count = bbsService.getTotalCount();
		paging.setTotalRecord(count);
		
		//	3. 전체 페이지 수를 구하자.
			//	전체 게시물의 수가 페이지당 개수보다 작으면
		if ( paging.getTotalRecord() <= paging.getNumPerPage() ) {
			//	페이지는 무조건 1페이지
			paging.setTotalPage(1);
		}else {
			//	전체게시물의 수를 페이지당 개수로 나눈 몫을 전체 페이지 수에 넣어주고
			paging.setTotalPage(paging.getTotalRecord()/paging.getNumPerPage());
			//	만약에 나머지가 있으면 페이지 수를 가져와서 1을 더해주자
			if (paging.getTotalRecord()%paging.getNumPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage() + 1);
			}
		}
		
		//	4. 현재 페이지를 구하기 => begin 과 end 를 구할수 있다.
		String cPage = request.getParameter("cPage");
			//	index 에서 선택하면 cPage 가 넘어오지 않는다.
			//	즉, 맨처음에는 cPage 가 없으므로 null 이다.
		if (cPage == null) {
			//	맨처음 오면 무조건 1 페이지 이다.
			paging.setNowPage(1);
		}else {
			paging.setNowPage(Integer.parseInt(cPage));
		}
		
		//	**여기서 부터는 오라클과 마리아DB 코딩이 달라진다.
		//	오라클	: begin, end
		//	마리아DB	: limit, offset
		
		//	건너뛸 행의 수
		//	offset = limit * (현재페이지 - 1)
		//	가져올 행의 수
		//	limit = numPerPage				
		paging.setOffset( paging.getNumPerPage() * (paging.getNowPage()-1) );
		
		//	시작블록과 끝블록 구하기
		paging.setBeginBlock(
			(int)(
					( (paging.getNowPage() -1) / paging.getPagePerBlock() ) 
					* paging.getPagePerBlock() 
					+ 1 )
				);
				//	시작블럭은
				//	1, 2, 3 까지는 1
				//	4, 5, 6 까지는 4
				//	7, 8, 9 까지는 7
		paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
				//	끝블럭은
				//	1, 2, 3 까지는 3
				//	4, 5, 6 까지는 6
				//	7, 8, 9 까지는 9
		
		//	**주의사항 
		//	endBlock 과 totalPage 가 다르면 (즉, endBlock 이 totalPage 보다 크면)
		//	endBlock 을 totalPage 로 지정하자. (쓸모없는 페이지를 날려버리자)
		if (paging.getEndBlock() > paging.getTotalPage()) {
			paging.setEndBlock( paging.getTotalPage() );
		}
		
		//	처리할거 다하고 DB 갔다오자
		List<BbsVO> bbs_list = bbsService.getBbsList(paging.getOffset(), paging.getNumPerPage());
		mv.addObject("bbs_list", bbs_list);
		mv.addObject("paging", paging);
		return mv;
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
				String f_name = uuid.toString() + "_" + file.getOriginalFilename();
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
	public ModelAndView getBbsDetail(String b_idx, String cPage) {
		ModelAndView mv = new ModelAndView("bbs/detail");
	
		//	조회수 증가
		int result = bbsService.getHitUpdate(b_idx);
		//	상세보기
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		
		//file.originalname = Buffer.from(file.originalname, 'ascii').toString('utf8' );
		if (result > 0 && bvo != null) {
			//	나중에 댓글 가져오기 (원글에 관련된 댓글만 => b_idx)
			List<CommentVO> comm_list = bbsService.getCommentList(b_idx);
			mv.addObject("comm_list", comm_list);
			mv.addObject("bvo", bvo);
			mv.addObject("cPage", cPage);
			return mv;
		}
		//	묶어서 처리하는 것을 트랜젝션이라고 한다.
		//	둘다 성공시만 커밋하고, 틀리면 롤백
		
		return new ModelAndView("bbs/error");
	}
	
	@GetMapping("bbs_down.do")
	public void getBbsDown(String f_name, HttpSession session, HttpServletResponse response) {
		try {
			String path = session.getServletContext()
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
	
	@PostMapping("comm_write.do")
	public ModelAndView getCommWrite(CommentVO cvo, @ModelAttribute("b_idx") String b_idx) {
		//	모델에 저장해놓으면 다음 페이지에서 받을 수 있다.
		//ModelAndView mv = new ModelAndView("redirect:bbs_detail.do?b_idx="+ cvo.getB_idx());
		ModelAndView mv = new ModelAndView("redirect:bbs_detail.do");
		
		int result = bbsService.getCommentInsert(cvo);
		
		return mv;
	}
	
	@PostMapping("comm_delete.do")
	public ModelAndView getCommDelete(String c_idx, @ModelAttribute("b_idx") String b_idx) {
		ModelAndView mv = new ModelAndView("redirect:bbs_detail.do");
		
		int result = bbsService.getCommentDelete(c_idx);
		
		return mv;
	}
	
	@PostMapping("bbs_delete.do")
	public ModelAndView getBbsDelete(@ModelAttribute("cPage") String cPage,
			@ModelAttribute("b_idx") String b_idx) {
		return new ModelAndView("bbs/delete");
	}
	
	@PostMapping("bbs_delete_ok.do")
	public ModelAndView getBbsDeleteOK(@RequestParam("pwd")String cpwd,
			@ModelAttribute("cPage")String cPage, @ModelAttribute("b_idx")String b_idx) {
		ModelAndView mv = new ModelAndView();
		
		//	비밀번호 체크
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		String dpwd = bvo.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("bbs/delete");
			mv.addObject("pwdchk", "fail");
			return mv;
		}else {
			//	원글 삭제하기 (그냥 삭제하면 외래키 때문에 오류발생)
			//	active 컬럼의 값을 1로 변경하자
			
			int result = bbsService.getBbsDelete(b_idx);
			if (result > 0) {
				mv.setViewName("redirect:bbs_list.do");
				return mv;
			}
		}
		return new ModelAndView("bbs/error");
	}
	
	@PostMapping("bbs_update.do")
	public ModelAndView getBbsUpdate(@ModelAttribute("cPage")String cPage,
			@ModelAttribute("b_idx")String b_idx) {
		ModelAndView mv = new ModelAndView("bbs/update");
		BbsVO bvo = bbsService.getBbsDetail(b_idx);
		if (bvo != null) {
			mv.addObject("bvo", bvo);
			return mv;
		}
		return new ModelAndView("bbs/error");
	}
	
	@PostMapping("bbs_update_ok.do")
	public ModelAndView getBbsUpdateOK(@ModelAttribute("bvo")BbsVO bvo, @ModelAttribute("cPage")String cPage
			, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String cpwd = bvo.getPwd();
		BbsVO bvo2 = bbsService.getBbsDetail(bvo.getB_idx());
		String dpwd = bvo2.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("bbs/update");
			mv.addObject("pwdchk", "fail");
			return mv;
		}else {
			try {
				String path = session.getServletContext().getRealPath("/resources/upload");
				MultipartFile file = bvo.getFile();
				if (file.isEmpty()) {
					bvo.setF_name(bvo.getF_name());
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid + "_" + file.getOriginalFilename();
					bvo.setF_name(f_name);
					
					byte[] in = file.getBytes();
					File out = new File(path, f_name);
					FileCopyUtils.copy(in, out);
				}
				
				int result = bbsService.getBbsUpdate(bvo);
				if (result > 0) {
					mv.addObject("b_idx", bvo.getB_idx());
					mv.setViewName("redirect:bbs_detail.do");
					return mv;
				}
				
				return mv;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return new ModelAndView("bbs/error");
	}
}













