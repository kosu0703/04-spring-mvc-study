package com.ict.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.bbs.dao.BbsVO;
import com.ict.board.dao.BoardVO;
import com.ict.board.service.BoardService;
import com.ict.common.Paging;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Paging paging;

	@RequestMapping("board_list.do")
	public ModelAndView boardList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board/list");

		// 페이징 기법
		// 1.
		// 전체 게시물의 수를 구하자
		int count = boardService.getTotalCount();
		paging.setTotalRecord(count);

		// 전체페이지의 수
		// 한페이지 당 게시물의 수보다 작으면 항상 1페이지
		if (paging.getTotalRecord() <= paging.getNumPerPage()) {
			paging.setTotalPage(1);
		} else {
			paging.setTotalPage(paging.getTotalRecord() / paging.getNumPerPage());
			if (paging.getTotalRecord() % paging.getNumPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage() + 1);
			}
		}

		// 2.
		// 현재페이지 구하자
		String cPage = request.getParameter("cPage");
		if (cPage == null) {
			paging.setNowPage(1);
		} else {
			paging.setNowPage(Integer.parseInt(cPage));
		}

		// 3.
		// offset 구하기
		// limit = numPerPage
		// offset = limit * (현재페이지 - 1)
		paging.setOffset(paging.getNumPerPage() * (paging.getNowPage() - 1));

		// 4.
		// 시작블록과 끝블록 구하기
		// 시작블록 = (int){(현재페이지 -1) / 페이지당 블록수} * 페이지당 블록수 + 1
		paging.setBeginBlock(
				(int) ((paging.getNowPage() - 1) / paging.getPagePerBlock()) * paging.getPagePerBlock() + 1);
		// 끝블록 = 시작블록 + 페이지당 블록수 - 1
		paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);

		// 끝블록이 전체페이지 수보다 크면 끝블록에 전체페이지 수를 넣어주자
		if (paging.getEndBlock() > paging.getTotalPage()) {
			paging.setEndBlock(paging.getTotalPage());
		}

		// 5.
		// DB 갔다오기
		List<BoardVO> board_list = boardService.getBoardList(paging.getOffset(), paging.getNumPerPage());

		// 6.
		// 리스트와 페이징처리정보를 넣어주자
		if (board_list != null) {
			mv.addObject("board_list", board_list);
			mv.addObject("paging", paging);
			return mv;
		}
		return new ModelAndView("board/error");
	}

	@GetMapping("board_write.do")
	public ModelAndView boardWrite(@ModelAttribute("cPage")String cPage) {
		return new ModelAndView("board/write");
	}

	@PostMapping("board_write_ok.do")
	public ModelAndView boardWriteOK(BoardVO bovo, HttpSession session,
			 @ModelAttribute("cPage")String cPage) {
		try {
			ModelAndView mv = new ModelAndView("redirect:board_list.do");
			String path = session.getServletContext().getRealPath("/resources/upload");
			MultipartFile file = bovo.getFile();
			if (file.isEmpty()) {
				bovo.setF_name("");
			} else {
				UUID uuid = UUID.randomUUID();
				String f_name = uuid + "_" + file.getOriginalFilename();
				bovo.setF_name(f_name);

				byte[] in = file.getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			bovo.setPwd(passwordEncoder.encode(bovo.getPwd()));

			int result = boardService.getBoardInsert(bovo);
			if (result > 0) {
				return mv;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("board/error");
	}

	@GetMapping("board_detail.do")
	public ModelAndView boardDetail(@ModelAttribute("cPage") String cPage, String bo_idx) {
		ModelAndView mv = new ModelAndView("board/detail");
		// 조회수 증가
		int result = boardService.getBoardHit(bo_idx);
		// 상세보기
		BoardVO bovo = boardService.getBoardDetail(bo_idx);
		if (result > 0 && bovo != null) {
			mv.addObject("bovo", bovo);
			return mv;
		}
		return new ModelAndView("board/error");

	}

	@PostMapping("ans_write.do")
	public ModelAndView boardAnsWrite(@ModelAttribute("cPage") String cPage, @ModelAttribute("bo_idx") String bo_idx) {
		return new ModelAndView("board/ans_write");
	}

	@PostMapping("board_ans_write_ok.do")
	public ModelAndView boardAnsWriteOK(@ModelAttribute("cPage") String cPage, BoardVO bovo, HttpSession session) {
		try {
			/* 답글에서만 처리할 일 */
			//	 답글 답글
			// 1. 원글의 groups, step, lev 를 가져오기
			BoardVO bovo2 = boardService.getBoardDetail(bovo.getBo_idx());
			int groups = Integer.parseInt(bovo2.getGroups());
			int step = Integer.parseInt(bovo2.getStep());
			int lev = Integer.parseInt(bovo2.getLev());

			// 2. step 과 lev 를 하나씩 증가시키자
			step++;
			lev++;

			// 3. DB 에서 lev 를 업데이트하자
			// ** 업데이트 조건은 groups 이 같은 글들을 찾아서
			// 기존 글들의 lev 이 같거나 크면
			// 기존 글들의 lev 를 증가시켜주자
			// (lev 에 의해 정렬을 하기 위해서)

			// => groups 와 lev 를 map 에 저장해서 DB 갔다오자
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("groups", groups);
			map.put("lev", lev);
			int result = boardService.getLevUpdate(map);
			bovo.setGroups(String.valueOf(groups));
			bovo.setStep(String.valueOf(step));
			bovo.setLev(String.valueOf(lev));

			ModelAndView mv = new ModelAndView("redirect:board_list.do");
			String path = session.getServletContext().getRealPath("/resources/upload");
			MultipartFile file = bovo.getFile();
			if (file.isEmpty()) {
				bovo.setF_name("");
			} else {
				UUID uuid = UUID.randomUUID();
				String f_name = uuid + "_" + file.getOriginalFilename();
				bovo.setF_name(f_name);

				byte[] in = file.getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			bovo.setPwd(passwordEncoder.encode(bovo.getPwd()));

			int result2 = boardService.getAnsInsert(bovo);
			if (result2 > 0) {
				return mv;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("board/error");
	}

	@PostMapping("board_delete.do")
	public ModelAndView boardDelete(@ModelAttribute("cPage") String cPage, 
			@ModelAttribute("bo_idx") String bo_idx) {
		return new ModelAndView("board/delete");
	}

	@PostMapping("board_delete_ok.do")
	public ModelAndView boardDeleteOK(@ModelAttribute("cPage") String cPage, BoardVO bovo,
			@ModelAttribute("bo_idx")String bo_idx) {
		ModelAndView mv = new ModelAndView("redirect:board_list.do");
		// 비밀번호 체크
		BoardVO bovo2 = boardService.getBoardDetail(bovo.getBo_idx());
		String dpwd = bovo2.getPwd();
		if (!passwordEncoder.matches(bovo.getPwd(), dpwd)) {
			mv.setViewName("board/delete");
			mv.addObject("pwdchk", "fail");
			return mv;
		} else {
			// 원글 삭제하기 (그냥 삭제하면 외래키 때문에 오류발생)
			// active 컬럼의 값을 1로 변경하자
			int result = boardService.getBoardDelete(bovo2);
			if (result > 0) {
				mv.setViewName("redirect:board_list.do");
				return mv;
			}
		}
		return new ModelAndView("board/error");
	}

	@PostMapping("board_update.do")
	public ModelAndView boardUpdate(@ModelAttribute("cPage") String cPage, String bo_idx) {
		ModelAndView mv = new ModelAndView("board/update");

		BoardVO bovo = boardService.getBoardDetail(bo_idx);
		if (bovo != null) {
			mv.addObject("bovo", bovo);
			return mv;
		}

		return new ModelAndView("board/error");
	}

	@PostMapping("board_update_ok.do")
	public ModelAndView boardUpdateOK(@ModelAttribute("cPage") String cPage, BoardVO bovo, 
			HttpSession session, @ModelAttribute("bo_idx")String bo_idx) {
		ModelAndView mv = new ModelAndView();

		BoardVO bovo2 = boardService.getBoardDetail(bovo.getBo_idx());
		String dpwd = bovo2.getPwd();
		if (!passwordEncoder.matches(bovo.getPwd(), dpwd)) {
			mv.setViewName("board/update");
			mv.addObject("pwdchk", "fail");
			bovo.setF_name(bovo.getOld_f_name());
			mv.addObject("bovo", bovo);
			return mv;
		} else {
			try {
				String path = session.getServletContext().getRealPath("/resources/upload");
				MultipartFile file = bovo.getFile();
				if (file.isEmpty()) {
					bovo.setF_name(bovo.getOld_f_name());
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid + "_" + file.getOriginalFilename();
					bovo.setF_name(f_name);
					
					byte[] in = file.getBytes();
					File out = new File(path, f_name);
					FileCopyUtils.copy(in, out);
				}	
				
				int result = boardService.getBoardUpdate(bovo);
				if (result > 0) {
					mv.setViewName("redirect:board_detail.do");
					return mv;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return new ModelAndView("board/error");
	}
	
	@GetMapping("board_down.do")
	public void down(String f_name, HttpSession session, HttpServletResponse response) {
		try {
			String path = session.getServletContext().getRealPath("/resources/upload/" + f_name);
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

}









