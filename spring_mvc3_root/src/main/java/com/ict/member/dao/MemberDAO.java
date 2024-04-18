package com.ict.member.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public MemberVO getLogin(MemberVO mvo) {
		try {
			return sqlSessionTemplate.selectOne("member.login", mvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public List<MemberVO> getMemberList(){
		try {
			return sqlSessionTemplate.selectList("member.ajax_list");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public String getIdChk(String m_id) {
		try {
			int result = sqlSessionTemplate.selectOne("member.ajax_idchk", m_id);
			//	중복 아이디가 존재하면
			if (result > 0) {
				return "0";
			}
			//	중복 아이디가 없으면
			return "1";
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getAjaxJoin(MemberVO mvo) {
		try {
			return sqlSessionTemplate.update("member.ajax_join", mvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	
	public int getAjaxDelete(String m_idx) {
		try {
			return sqlSessionTemplate.delete("member.ajax_delete", m_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	
	
}
