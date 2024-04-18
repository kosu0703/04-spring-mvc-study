package com.ict.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class LoginInterceptor implements AsyncHandlerInterceptor{
	
	//	1. preHandle
	//	컨트롤러로 가기전에 구동된다.
	//	결과가 true 이면 Controller로 이동, 결과가 false 이면 특정 JSP 로 이동
	//	컨트롤러 이전에 처리해야 하는 전처리 작업이나 요청 정보를 가공하거나 추가하는 경우에 사용
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//	로그인 체크를 해서 만약에 로그인이 안된 상태이면 value 에 false 저장
		
		//	만약에 세션이 삭제된 상태라면 새로운 세션을 생성해 준다.
		//	세션이 삭제가 안된 상태라면 사용하고 있는 세션을 그대로 전달해준다.
		//	(즉, 로그인 했으면 만들어진 세션을 가져오고, 로그인 안했으면 세션을 새롭게 만들어준다.) 
		HttpSession session = request.getSession(true);
		
		Object obj = session.getAttribute("loginChk");
		if (obj == null) {
			//	세션을 새로만들었을 경우 (로그인 안됐을때)
			//	value 로 false 를 주고, 특정 jsp 페이지로 보낸다.
			request.getRequestDispatcher("/WEB-INF/views/member/login_error.jsp").forward(request, response);
			return false;
		}else {
			
		}
		return true;
	}
	
	/*
	//	2. postHandle
	//	컨트롤러에서 리턴되어 뷰 리졸버로 가기전에 구동된다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		AsyncHandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	//	3. afterCompletion
	//	뷰 리졸버가 뷰를 찾아서 보내고 나면 구동된다.
	//	모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행된다. (뷰 랜더링 후)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	*/
}
