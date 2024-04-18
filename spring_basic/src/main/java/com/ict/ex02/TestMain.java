package com.ict.ex02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestMain {
	public static void main(String[] args) {
		//	DI 는 Spring Container 에서 객체(Bean) 를 생성하고 관리한다.
		
		//	Spring Container 에게 필요한 정보를 제공해야 한다.
		//		=> 그 정보는 설정 정보(Configuration Metadata) 에 있다.
		
		//	Configuration Metadata 는 기본적으로 XML 파일 형태이다.
		//							추가적으로 Annotation 을 이용한다.
		//			(하지만 나중에 Spring boot 에서는 무조건 Annotation 을 사용한다.)
		
		//	Spring Containner => BeanFactory => ApplicationContext	(일반적) 스프링프레임워크
		//									 => WebApplicationContext (웹에서)
		
		//	ApplicationContext context = 
		//		new GenericXmlApplicationContext("설정 정보 위치")
		
		//	설정 정보는 xml 파일로 만드는데 Spring Bean Configuration File 로 만든다.
		
		//	**스프링 컨테이너 (어플리케이션 컨텍스트) 가 설정정보 xml 파일을 읽어야만 객체가 생성된다.
		//		설정정보 위치는 DBService 에서 읽었던 것처럼 슬래시 / 를 사용한다.
		
		ApplicationContext context =
				new GenericXmlApplicationContext("com/ict/ex02/config.xml");
		
		//	스프링컨테이너 context 안에있는 객체를 id로 끄집어내서 사용한다.
		Service service = (Service) context.getBean("service");
		service.biz();
		
	}
}
