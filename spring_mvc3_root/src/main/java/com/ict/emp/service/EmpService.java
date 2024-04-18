package com.ict.emp.service;

import java.util.List;

import com.ict.emp.dao.EmpVO;

public interface EmpService {

	//	전체보기
	public List<EmpVO> getEmpList() throws Exception;
	
	//	동적쿼리 검색
	public List<EmpVO> getEmpSearchList(String searchType, String searchValue) throws Exception;
	
	
}
