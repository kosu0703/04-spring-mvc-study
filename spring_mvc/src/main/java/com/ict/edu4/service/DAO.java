package com.ict.edu4.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class DAO {
	
	//	root-context.xml 에서 객체생성 했으므로 생성안해도된다.
	
	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	//	리스트
	public List<VO> getList() {
		try {
			List<VO> list = null;
			list = sqlSessionTemplate.selectList("members.list");
			return list;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	
}
