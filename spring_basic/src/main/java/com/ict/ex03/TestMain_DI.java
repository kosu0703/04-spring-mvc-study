package com.ict.ex03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestMain_DI {
	public static void main(String[] args) {
		//	xml 파일을 읽어서 객체가 생성된다.
		ApplicationContext context = 
				new GenericXmlApplicationContext("com/ict/ex03/config.xml");
		//	xml 파일 안에 있는 모든 객체가 생성된다.
	
		MyProcess process = (MyProcess) context.getBean("mp3");
		process.prn();
		
	}
}
