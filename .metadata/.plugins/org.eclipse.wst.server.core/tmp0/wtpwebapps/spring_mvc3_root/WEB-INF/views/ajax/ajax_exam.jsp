<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table, th, td {
		border: 1px solid red;
		border-collapse: collapse;
		text-align: center;
		margin: 0 auto;
		padding: 10px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//	text 
		$("#btn1").click(function() {
			//alert("눌렀네");
			$("#result").empty();
			$.ajax({
				url : "test01.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "text" ,			//	가져오는 결과 타입
				//data : 		,			//	서버에 보낼때 같이 가는 데이터 (파라미터)
				//async : true 또는 false	 ,	//	비동기(기본, 생략, true) , 동기(false)
				success : function (data) {
					$("#result").append(data);		//	성공하면 result 에 담자
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});
//==================================================================================
		$("#btn2").click(function() {
			//alert("btn2");
			$("#result").empty();
			$.ajax({
				url : "test02.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "xml" ,			//	가져오는 결과 타입
				//data : 		,			//	서버에 보낼때 같이 가는 데이터 (파라미터)
				//async : true 또는 false	 ,	//	비동기(기본, 생략, true) , 동기(false)
				success : function (data) {
					let table = "<table>";
					table += "<thead><tr><th>종류</th><th>가격</th></tr></thead>";
					table += "<tbody>";
					
					//	데이터가 여러개이므로 find 로 태그를 찾는다.
					$(data).find("product").each(function() {
						let name = $(this).find("name").text();
						let price = $(this).find("price").text();
						table += "<tr>";
						table += "<td>" + name + "</td>";
						table += "<td>" + price + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});
//==================================================================================
		//	다른방법
		$("#btn3").on("click", function() {
			//alert("btn3");
			$("#result").empty();
			$.ajax({
				url : "test03.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "xml" ,			//	가져오는 결과 타입
				//data : 		,			//	서버에 보낼때 같이 가는 데이터 (파라미터)
				//async : true 또는 false	 ,	//	비동기(기본, 생략, true) , 동기(false)
				success : function (data) {
					let table = "<table>";
					table += "<thead><tr><th>차량 종류</th><th>차량 개수</th></tr></thead>";
					table += "<tbody>";
					
					$(data).find("product").each(function() {
						//	attr 로 속성을 찾자
						let name = $(this).attr("name");
						let count = $(this).attr("count");
						table += "<tr>";
						table += "<td>" + name + "</td>";
						table += "<td>" + count + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);		//	성공하면 result 에 담자
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});
//==================================================================================
		$("#btn4").on("click", function() {
			//alert("btn4");
			$("#result").empty();
			$.ajax({
				url : "test04.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "xml" ,			//	가져오는 결과 타입
				//data : 		,			//	서버에 보낼때 같이 가는 데이터 (파라미터)
				//async : true 또는 false	 ,	//	비동기(기본, 생략, true) , 동기(false)
				success : function (data) {
					let table = "<table>";
					table += "<thead><tr><th>회사</th><th>차량 종류</th><th>차량 개수</th></tr></thead>";
					table += "<tbody>";
					
					$(data).find("product").each(function() {
						let company = $(this).text();
						let name = $(this).attr("name");
						let count = $(this).attr("count");
						table += "<tr>";
						table += "<td>" + company + "</td>";
						table += "<td>" + name + "</td>";
						table += "<td>" + count + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);		//	성공하면 result 에 담자
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});
//==================================================================================
		$("#btn5").on("click", function() {
			$("#result").empty();
			$.ajax({
				url : "test05.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "xml" ,			//	가져오는 결과 타입
				success : function(data) {
					let table = "<table>";
					table += "<thead><tr>"
					table += "<th colspan='4'>"
					table += $(data).find("weather").attr("year") + "년 ";
					table += $(data).find("weather").attr("month") + "월 ";
					table += $(data).find("weather").attr("day") + "일 ";
					table += $(data).find("weather").attr("hour") + "시 ";
					table += "</th></tr>";
					table += "<tr><th>지역</th><th>온도</th><th>상태</th><th>아이콘</th>"
					table += "</tr></thead>";
					table += "<tbody>";
					
					$(data).find("local").each(function() {
						let local = $(this).text();
						let ta = $(this).attr("ta");
						let desc = $(this).attr("desc");
						let icon = $(this).attr("icon");
						table += "<tr>";
						table += "<td>" + local + "</td>";
						table += "<td>" + ta + "</td>";
						table += "<td>" + desc + "</td>";
						table += "<td><img src = 'http://www.kma.go.kr/images/icon/NW/NB" 
									+ icon + ".png'></td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				},
				error : function() {
					alert("읽기실패");
				}
			});
		});
//==================================================================================
		$("#btn6").on("click", function() {
			$("#result").empty();
			$.ajax({
				url : "test06.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "json" ,			//	가져오는 결과 타입
				success : function(data) {
					let table = "<table>";
					table += "<thead><tr><th>이름</th><th>범위</th></tr></thead>";
					table += "<tbody>";
					
					//	key 가 인덱스고, value 가 객체이다.
					$.each(data, function(index, object) {
						//console.log("key", index);
						//console.log("value", object);
						let name = object.name;
						let scope = object.scope;
						table += "<tr>";
						table += "<td>" + name + "</td>";
						table += "<td>" + scope + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});	
//==================================================================================
		$("#btn7").on("click", function() {
			$("#result").empty();
			$.ajax({
				url : "test07.do" ,			// 	서버주소
				method : "post" ,			//	전달방식
				dataType : "json" ,			//	가져오는 결과 타입
				success : function(data) {
					let table = "<table>";
					table += "<thead><tr>";
					table += "<th>시·도별(1)</th>";
					table += "<th>총인구 (명)</th>";
					table += "<th>1차 접종 누계</th>";
					table += "<th>2차 접종 누계</th>";
					table += "<th>1차 접종 퍼센트</th>";
					table += "<th>2차 접종 퍼센트</th>";
					table += "</tr></thead>";
					table += "<tbody>";
					
					$.each(data, function(index, obj) {
						//	자바스크립트에서 천단위 코마(내장함수)
						// 	천단위 콤마(내장함수) : toLocaleString()
						//	소수점 2자리까지 
						//	: toLocaleString('ko-KR', {maximumFractionDigits:2})
						table += "<tr>";
						table += "<td>" + obj['시·도별(1)'] + "</td>";
						table += "<td>" + obj['총인구 (명)'].toLocaleString() + "</td>";
						table += "<td>" + obj['1차 접종 누계'].toLocaleString() + "</td>";
						table += "<td>" + obj['2차 접종 누계'].toLocaleString() + "</td>";
						table += "<td>" + obj['1차 접종 퍼센트'].toLocaleString('ko-KR', {maximumFractionDigits:2}) + "</td>";
						table += "<td>" + obj['2차 접종 퍼센트'].toLocaleString('ko-KR', {maximumFractionDigits:2}) + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				},
				error : function() {
					alert("읽기 실패");
				}
			});
		});	
//==================================================================================
	});
</script>
</head>
<body>
	
	<h2>Ajax 연습하는 장소</h2>
	
	<button id="btn1">text</button>
	<button id="btn2">xml_01</button>
	<button id="btn3">xml_02</button>
	<button id="btn4">xml_03</button>
	<button id="btn5">날씨_xml</button>
	<button id="btn6">json_01</button>
	<button id="btn7">json_02</button>
	
	<hr>
	<div id="result">
		
	</div>
	
</body>
</html>