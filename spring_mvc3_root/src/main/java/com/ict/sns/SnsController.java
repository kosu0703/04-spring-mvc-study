package com.ict.sns;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ict.sns.kakao.KakaoVO;
import com.ict.sns.naver.NaverVO;

@Controller
public class SnsController {

	@Autowired	
	private AddrDAO addrDAO;
	
	@RequestMapping("spring_sns.do")

	public ModelAndView getSpringSns() {
		return new ModelAndView("sns/loginForm");
	}

	// 카카오 로그인
	@RequestMapping("kakaologin.do")
	public ModelAndView kakaoLogin(HttpServletRequest request) {
		// 1. 인가 코드 받기
		String code = request.getParameter("code");

		// 2. 토큰 받기 (인가 코드 가지고)
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// 헤더 요청
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

			// 본문
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			StringBuffer sb = new StringBuffer();

			sb.append("grant_type=authorization_code");
			sb.append("&client_id=84f22c8078987862ac7f5051394c4959");
			sb.append("&redirect_uri=http://localhost:8090/kakaologin.do");
			sb.append("&code=" + code);

			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {

				// 토큰 요청을 통한 결과를 얻는데 JSON 타입이다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				StringBuffer sb2 = new StringBuffer();

				// 한줄씩 읽다가 더이상 읽을 수 없으면
				while ((line = br.readLine()) != null) {
					sb2.append(line);
				}

				// 결과를 보자
				String result = sb2.toString();
				// System.out.println(result);

				// 3. 사용자 정보 가져오기 (토큰을 이용해서 )
				// 받은 정보는 session 저장 ( ajax 를 사용하기 위해서 )
				Gson gson = new Gson();
				KakaoVO kvo = gson.fromJson(result, KakaoVO.class);

				request.getSession().setAttribute("access_token", kvo.getAccess_token());
				request.getSession().setAttribute("token_type", kvo.getToken_type());
				request.getSession().setAttribute("refresh_token", kvo.getRefresh_token());

				return new ModelAndView("sns/result");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return new ModelAndView("sns/error");
	}

	// 네이버 로그인
	@RequestMapping("naver_login.do")
	public ModelAndView naverLogin(HttpServletRequest request) {
		// 1. 인가 코드 받기
		String code = request.getParameter("code");

		// 2. 토큰 받기 (인가 코드 가지고)
		String reqURL = "https://nid.naver.com/oauth2.0/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// 헤더 요청
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

			// 본문
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			StringBuffer sb = new StringBuffer();

			sb.append("grant_type=authorization_code");
			sb.append("&client_id=YBlxtHy5leLiBnTL_u06");
			sb.append("&client_secret=Gi6JIdaE97");
			sb.append("&code=" + code);
			sb.append("&state=test");

			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {

				// 토큰 요청을 통한 결과를 얻는데 JSON 타입이다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				StringBuffer sb2 = new StringBuffer();

				// 한줄씩 읽다가 더이상 읽을 수 없으면
				while ((line = br.readLine()) != null) {
					sb2.append(line);
				}

				// 결과를 보자
				String result = sb2.toString();
				System.out.println(result);

				// 3. 사용자 정보 가져오기 (토큰을 이용해서 )
				// 받은 정보는 session 저장 ( ajax 를 사용하기 위해서 )
				Gson gson = new Gson();
				NaverVO nvo = gson.fromJson(result, NaverVO.class);

				request.getSession().setAttribute("access_token", nvo.getAccess_token());
				request.getSession().setAttribute("token_type", nvo.getToken_type());
				request.getSession().setAttribute("refresh_token", nvo.getRefresh_token());

				return new ModelAndView("sns/result2");

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return new ModelAndView("sns/error");
	}

	// 네이버 로그아웃(연동해제)
	@RequestMapping("naverlogout.do")
	public ModelAndView naverLogout(HttpSession session) {
		/*
		// 연동해제 = 회원탈퇴
		String apiURL = "https://nid.naver.com/oauth2.0/token" + "?grant_type=delete"
				+ "&client_id=YBlxtHy5leLiBnTL_u06" + "&client_secret=Gi6JIdaE97" + "&access_token="
				+ session.getAttribute("access_token") + "&service_provider='NAVER'"; // 개발자 페이지 예제에는 안적혀있다.

		try {
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode);
			// 200 => 성공

			if (responseCode == HttpURLConnection.HTTP_OK) {

				// 토큰 요청을 통한 결과를 얻는데 JSON 타입이다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				StringBuffer sb = new StringBuffer();

				// 한줄씩 읽다가 더이상 읽을 수 없으면
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				// 결과를 보자
				String result = sb.toString();
				System.out.println(result);
				// {"result":"success",
				// "access_token":"AAAAObPFdaAEFqIjBWPjHw6YIOF93F9x0qytQbwx_1Gm2f6S6yYD5FGpN3G5yIKA9AeBVOsctv4d3N6K6ZYlj7IpUsQ"}
				
				}
				
			} catch (Exception e) {
			
			}
		*/

		// 세션에 저장한 것도 비워주자(세션 초기화)
		session.invalidate();

		// 다시 로그인 폼으로
		return new ModelAndView("sns/loginForm");
	}

	// 카카오 로그아웃(화면이동만, 처리는 api가 해줌)
	@RequestMapping("kakaologout.do")
	public ModelAndView kakaoLogout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("sns/loginForm");
	}

	@RequestMapping("kakao_map01.do")
	public ModelAndView kakaoMap01() {
		return new ModelAndView("sns/kakao_map01");
	}
	
	@RequestMapping("kakao_map02.do")
	public ModelAndView kakaoMap02() {
		return new ModelAndView("sns/kakao_map02");
	}
	
	@RequestMapping("kakao_map03.do")
	public ModelAndView kakaoMap03() {
		return new ModelAndView("sns/kakao_map03");
	}
	
	@RequestMapping("kakao_map04.do")
	public ModelAndView kakaoMap04() {
		return new ModelAndView("sns/kakao_map04");
	}
	
	@RequestMapping("kakao_addr.do")
	public ModelAndView kakaoAddr() {
		return new ModelAndView("sns/kakao_addr");
	}
	
	//	주소 정보를 받자
	@RequestMapping("kakao_addr_ok.do")
	public ModelAndView kakaoAddrOK(AddrVO avo) {
		try {
			//	DB 에 등록
			int result = addrDAO.addrInsert(avo);
			if (result > 0) {
				return new ModelAndView("sns/loginForm");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("sns/error");
	}
	
	//	동적쿼리 연습
	@RequestMapping("dynamic_query.do")
	public ModelAndView dynamicQuery() {
		return new ModelAndView("emp/dynamicQuery");
	}
	
	
}
