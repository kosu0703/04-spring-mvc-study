package com.ict.sns.kakao;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class KakaoAjaxController2 {
	
	@RequestMapping(value = "kakao_user2.do", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String memberChk(HttpSession session) {
		
		//	세션에 있는 access_token 을 가지고 사용자 정보를 가져올 수 있다.
		String access_token = (String) session.getAttribute("access_token");
		
		//	유저 정보 가지러 가기
		String apiURL = "https://kapi.kakao.com/v2/user/me";
		
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(apiURL);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			//	요청 : 엑세스 토큰 방식
			//	필수 - 헤더 (Authorization, Content-type)
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			
			//	200 이면 성공과 같은 의미 (HttpURLConnection.HTTP_OK)
			int responseCode = conn.getResponseCode();
				
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = 
						new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line = "";
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();
				
				Gson gson = new Gson();
				//	json 을 객체로 변경 => fromJson(변경할 json, 변경된 객체)
				KakaoUserVO kvo = gson.fromJson(result, KakaoUserVO.class);
				
				//	객체에서 정보를 가져와서 DB 에 넣어줘야한다.
				String kakao_id = kvo.getId();
				String kakao_nickname = kvo.getProperties().getNickname();
				String kakao_email = kvo.getKakao_account().getEmail();
				
				//	DB 에 저장하기
				
				
				
				//	ajax 에서는 text 로 받아서 / 슬래시로 쪼갠다.
				return kakao_id + "/" + kakao_nickname + "/" + kakao_email;
			}
			
			
		} catch (Exception e) {
			System.out.println("연결실패");
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e2) {
			}
		}
		
		return null;
	}
	
	
	
}
