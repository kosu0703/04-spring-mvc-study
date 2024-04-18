package com.ict.ex02;

public class Service {
	//	1. OracleDAO oracleDAO = new OracleDAO();
	//	2. MariaDBDAO mariaDBDAO = new MariaDBDAO();
	
	//	인터페이스(클래스)를 자료형으로 사용
	private DAO dao;
	
	public Service() {}
	public Service(DAO dao) {
		//	생성자에 인자가 있으면 전역변수로 넘겨줘야한다
		this.dao = dao;
	}
	public void setDao(DAO dao) {
		this.dao = dao;
	}
	//	사용하고 싶은 메서드
	public void biz() {
		//	오라클DAO , 마리아디비DAO => prn() 메서드 실행
		//	1. oracleDAO.prn();
		//	2. mariaDBDAO.prn();
		dao.prn();
	}
}
