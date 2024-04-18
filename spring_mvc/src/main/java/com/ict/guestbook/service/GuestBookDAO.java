package com.ict.guestbook.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class GuestBookDAO {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public List<GuestBookVO> getList() {
		try {
			List<GuestBookVO> list = null;
			list = sqlSessionTemplate.selectList("guestbook.list");
			return list;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getInsert(GuestBookVO vo) {
		try {
			int result = 0;
			result = sqlSessionTemplate.insert("guestbook.insert", vo);
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		//	0 이면 삽입이 안됐어도 오류가 안나므로
		//	음수를 줘서 오류를 나게하자
		return -1;
	}
	
	public GuestBookVO getDetail(String idx) {
		try {
			GuestBookVO vo = null;
			vo = sqlSessionTemplate.selectOne("guestbook.detail", idx);			
			return vo;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getDelete(String idx) {
		try {
			int result = 0;
			result = sqlSessionTemplate.delete("guestbook.delete", idx);
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public int getUpdate(GuestBookVO vo) {
		try {
			int result = 0;
			result = sqlSessionTemplate.update("guestbook.update", vo);
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
}
