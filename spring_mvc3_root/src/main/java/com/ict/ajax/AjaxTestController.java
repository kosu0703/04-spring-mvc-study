package com.ict.ajax;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//	ajax 컨트롤러는 @RestController 를 사용한다.
@RestController
public class AjaxTestController {
	
	//	**servlet-context.xml (디스패쳐서블릿) 으로 리턴되지 않고 브라우저에 바로 출력된다.
	
	//	1.
	//	반환형이 String 인 경우 문서타입이 contentType="text/html" 타입으로 알아서 처리된다.
	@RequestMapping(value = "test01.do", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String Text_Exam01() {
		String msg = "<h2>Hello World Spring Ajax !! <br>환영합니다.</h2>";
		return msg;
	}
	
	//	2.
	//	xml 타입의 파일을 가져온다.									//	한글처리
	@RequestMapping(value = "test02.do", produces = "text/xml; charset=utf-8")
	//	브라우저 바디
	@ResponseBody
	public String Xml_Exam01() {
		StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<products>");
        sb.append("<product>");
        sb.append("<name>흰우유</name>");
        sb.append("<price>950</price>");
        sb.append("</product>");
        sb.append("<product>");
        sb.append("<name>딸기우유</name>");
        sb.append("<price>1050</price>");
        sb.append("</product>");
        sb.append("<product>");
        sb.append("<name>초코우유</name>");
        sb.append("<price>1100</price>");
        sb.append("</product>");
        sb.append("<product>");
        sb.append("<name>바나나우유</name>");
        sb.append("<price>1550</price>");
        sb.append("</product>");
        sb.append("</products>");
        return sb.toString();
	}
	
	//	3.
	//	속성으로만 이루어진 xml
	@RequestMapping(value = "test03.do", produces = "text/xml; charset=utf-8")
	@ResponseBody
	public String Xml_Exam02() {
		 	StringBuffer sb = new StringBuffer();
		 			//	자바에서는 역슬래시 \ 로 쌍따옴표를 표시한다.
		 			//	<?xml version="1.0" encoding="UTF-8"?>
	        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        sb.append("<products>");
	        sb.append("<product count=\"5\" name=\"제너시스\" />");
	        sb.append("<product count=\"7\" name=\"카렌스\" />");
	        sb.append("<product count=\"9\" name=\"카니발\" />");
	        sb.append("<product count=\"5\" name=\"카스타\" />");
	        sb.append("<product count=\"2\" name=\"트위치\" />");
	        sb.append("</products>");
	        return sb.toString();
	}
	
	//	4.
	//	속성과 데이터로 이루어진 xml 
	@RequestMapping(value = "test04.do", produces = "text/xml; charset=utf-8")
	@ResponseBody
	public String Xml_Exam03() {
		StringBuffer sb = new StringBuffer();
		//	자바에서는 역슬래시 \ 로 쌍따옴표를 표시한다.
		//	<?xml version="1.0" encoding="UTF-8"?>
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<products>");
		sb.append("<product count=\"5\" name=\"제너시스\">현대자동차</product>");
		sb.append("<product count=\"7\" name=\"카렌스\">기아자동차</product>");
		sb.append("<product count=\"9\" name=\"카니발\">기아자동차</product>");
		sb.append("<product count=\"5\" name=\"카스타\">기아자동차</product>");
		sb.append("<product count=\"2\" name=\"트위치\">르노자동차</product>");
		sb.append("</products>");
		return sb.toString();
	}
	
	//	5.
	//	xml 공공데이터 가져오기 
	@RequestMapping(value = "test05.do", produces = "text/xml; charset=utf-8")
	@ResponseBody
	public String Xml_Weather() {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			//	외부 인터넷 서버 접속하기
			URL url = new URL("http://www.kma.go.kr/XML/weather/sfc_web_map.xml");
			//	접속하기
			URLConnection conn = url.openConnection();
			//	데이터 가져오기, 한글처리
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			//	한줄씩 읽을 수 있다.
			String msg = "";
			while ( (msg = br.readLine()) != null ) {
				sb.append(msg);
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//	6. 
	//	json 가져오기
	//	key : value
	@RequestMapping(value = "test06.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String Json_Exam() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");					//	key : value
		sb.append(" {\"name\" : \"HTML\", \"scope\" : \"15\"},");
		sb.append(" {\"name\" : \"CSS\", \"scope\" : \"10\"},");
		sb.append(" {\"name\" : \"JavaScript\", \"scope\" : \"17\"},");
		sb.append(" {\"name\" : \"JSP\", \"scope\" : \"13\"}");
		sb.append("]");
		return sb.toString();
	}
	
	//	7. 
	//	공공데이터 json 가져오기
	@RequestMapping(value = "test07.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String Json_Corona() {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			//	외부 인터넷 서버 접속하기
			URL url = new URL("https://raw.githubusercontent.com/paullabkorea/coronaVaccinationStatus/main/data/data.json");
			//	접속하기
			URLConnection conn = url.openConnection();
			//	데이터 가져오기, 한글처리
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			//	한줄씩 읽을 수 있다.
			String msg = "";
			while ( (msg = br.readLine()) != null ) {
				sb.append(msg);
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//	8.
	//	공공데이터 포털 - 미세먼지 xml
	@RequestMapping(value = "dust_data.do", produces = "text/xml; charset=utf-8")
	@ResponseBody
	public String getDustData() {
		try {
			
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Rs2hCA9I6Y5q4TlZWwvkT+Kpf/E42e4y5TcRt9HlhfxZzg6r/Zb7PyaQBN/v183KSU91M9jKg8OvM6pN2TAMAw=="); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml 또는 json*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
	        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*버전별 상세 결과 참고*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-type", "text/xml");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
	        return sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//	9.
	//	공공데이터 포털 - 미세먼지 json
	@RequestMapping(value = "dust_data2.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDustData2() {
		try {
			
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Rs2hCA9I6Y5q4TlZWwvkT+Kpf/E42e4y5TcRt9HlhfxZzg6r/Zb7PyaQBN/v183KSU91M9jKg8OvM6pN2TAMAw=="); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml 또는 json*/
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
			urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
			urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*버전별 상세 결과 참고*/
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "text/xml");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
}


















