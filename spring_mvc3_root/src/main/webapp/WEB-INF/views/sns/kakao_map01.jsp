<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 지도 첫번째 연습</title>
</head>
<body>

<%--
	카카오 개발자 사이트
	
	1. 내 애플리케이션 - JavaScript 키 - d514432bb9f7ee5d4a28d62d4ccba6b8
	
	2. 제품 - 지도/로컬 - 문서보기 - Maps API 참고하기 - Web - 시작하기
	
	3. 좌측 Sample 클릭 
	
	4. 지도 생성하기 JavaScript + HTML 눌러서 body 부분만 복사하자
	
	5. 발급받은 APP KEY를 사용하세요 => 복사한 자바스크립트 키 넣어주기
	
--%>
	
	<!-- 지도를 표시할 div 입니다 -->
	<div id="map" style="width:100%;height:350px;"></div>
	
	<script type="text/javascript" 
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d514432bb9f7ee5d4a28d62d4ccba6b8">
	</script>
	
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = { 
		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		        level: 3 // 지도의 확대 레벨
		    };
		
		// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption); 
	</script>
	
</body>
</html>