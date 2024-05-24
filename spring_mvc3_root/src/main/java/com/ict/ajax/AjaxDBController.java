package com.ict.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ict.member.dao.MemberVO;
import com.ict.member.service.MemberService;

@RestController
public class AjaxDBController {
	
	@Autowired
	private MemberService memberService;
	
	//	xml 로 DB 처리하기
	@RequestMapping(value = "getAjaxList.do", produces = "text/xml; charset=utf-8")
	@ResponseBody
	public String getAjaxList() {
		//	DB 갔다오기 
		//	전체 정보 가져오기
		List<MemberVO> list = memberService.getMemberList();
		//	리스트면 size 로 체크해도된다.
		if (list != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<members>");
			
			for (MemberVO k : list) {
				sb.append("<member>");
				sb.append("<m_idx>" + k.getM_idx() + "</m_idx>");
				sb.append("<m_id>" + k.getM_id() + "</m_id>");
				sb.append("<m_pw>" + k.getM_pw() + "</m_pw>");
				sb.append("<m_name>" + k.getM_name() + "</m_name>");
				sb.append("<m_age>" + k.getM_age() + "</m_age>");
				sb.append("<m_reg>" + k.getM_reg().substring(0, 10) + "</m_reg>");
				sb.append("</member>");
			}
			
			sb.append("</members>");
			return sb.toString();
		}
		return "fail";
	}
	
	//	json 으로 DB 처리하기
	@RequestMapping(value = "getAjaxList2.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAjaxList2() {
		//	DB 갔다오기 
		//	전체 정보 가져오기
		List<MemberVO> list = memberService.getMemberList();
		//	리스트면 size 로 체크해도된다.
		if (list != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(list);
			return jsonString;
		}
		return "fail";
	}
	
	//	아이디 중복 체크하기
	@RequestMapping(value = "getAjaxIdChk.do", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxIdChk(String m_id) {
		//System.out.println("m_id : " + m_id);
		
		//	DB 갔다오기
		String result = memberService.getIdChk(m_id);
		return result;
	}
	
	//	가입성공 여부 체크하기
	@RequestMapping(value = "getAjaxJoin.do", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxJoin(MemberVO mvo) {
		int result = memberService.getAjaxJoin(mvo);
		return String.valueOf(result);
	}
	
	//	삭제버튼 누르면 회원 삭제하기
	@RequestMapping(value = "getAjaxDelete.do", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxDelete(String m_idx) {
		int result = memberService.getAjaxDelete(m_idx);
		return String.valueOf(result);
	}
	
	
	
	
	
	
}
