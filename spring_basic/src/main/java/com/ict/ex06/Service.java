package com.ict.ex06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Service {
	
	//	@Autowired : **변수이름과 참조하는 클래스의 ID가 같을때 사용
	//		(이름이 다르면 오류발생)
	@Autowired
	//	@Qualifier("ID") : **만약에 같은 이름이 없을때는 참조하는 클래스의 ID를 입력해준다.
	@Qualifier("oracleDAO")
	private DAO dao;
	
	public Service() {}
	public Service(DAO dao) {
		this.dao = dao;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
	
	//	사용하고 싶은 메서드
	public void biz() {
		dao.prn();
	}
}
