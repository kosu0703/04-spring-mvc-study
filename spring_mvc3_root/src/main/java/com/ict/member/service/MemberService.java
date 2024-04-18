package com.ict.member.service;

import java.util.List;

import com.ict.member.dao.MemberVO;

public interface MemberService {
	
	//	로그인
	public MemberVO getLogin(MemberVO mvo);
	
	//	전체보기
	public List<MemberVO> getMemberList();
	
	//	아이디 중복 검사
	public String getIdChk(String m_id);
	
	//	가입 성공 여부 검사
	public int getAjaxJoin(MemberVO mvo);
	
	//	삭제버튼 누르면 회원정보 삭제하기
	public int getAjaxDelete(String m_idx);
}
