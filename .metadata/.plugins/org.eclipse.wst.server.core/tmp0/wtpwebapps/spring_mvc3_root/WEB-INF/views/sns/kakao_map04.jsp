<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 지도 네번째 연습(키워드로 장소검색하기)</title>
				<!-- &libraries=services 앱키에 추가해줘야한다. -->
<script type="text/javascript" 
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d514432bb9f7ee5d4a28d62d4ccba6b8&libraries=services">
</script>
<script type="text/javascript">
	/*
	//	위도(latitude)와 경도(longitude) 구하기
	//	navigator.geolocation.getCurrentPosition();
	navigator.geolocation.getCurrentPosition(function(position) {
		let lat = position.coords.latitude;
		let lng = position.coords.longitude;
		//	함수 호출
		//geo_map(lat, lng);
	});	
	*/
	function search_go() {
		let keyword = document.getElementById("keyword").value;
		geo_map(keyword);
	}
	
</script>
</head>
<body>

	<!-- 서버가는게 아니여서 form 태그 사용안함 -->	
	<h1>키워드로 장소 검색</h1>
	<input type="text" name="keyword" id="keyword">
	<button type="button" onclick="search_go()">검색</button>
	<br><br><br><br><br>
	
	<!-- 지도를 표시할 div 입니다 -->
	<div id="map" style="width:100%;height:350px;"></div>
	
	<script>
	
		function geo_map(keyword) {
			// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
			var infowindow = new kakao.maps.InfoWindow({zIndex:1});

			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			    mapOption = {
			        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			        level: 3 // 지도의 확대 레벨
			    };  

			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer, mapOption); 

			// 장소 검색 객체를 생성합니다
			var ps = new kakao.maps.services.Places(); 

			// 키워드로 장소를 검색합니다
			ps.keywordSearch(keyword, placesSearchCB); 

			// 키워드 검색 완료 시 호출되는 콜백함수 입니다
			function placesSearchCB (data, status, pagination) {
			    if (status === kakao.maps.services.Status.OK) {

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        var bounds = new kakao.maps.LatLngBounds();

			        for (var i=0; i<data.length; i++) {
			            displayMarker(data[i]);    
			            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
			        }       

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			        map.setBounds(bounds);
			    } 
			}

			// 지도에 마커를 표시하는 함수입니다
			function displayMarker(place) {
			    
			    // 마커를 생성하고 지도에 표시합니다
			    var marker = new kakao.maps.Marker({
			        map: map,
			        position: new kakao.maps.LatLng(place.y, place.x) 
			    });

			    // 마커에 클릭이벤트를 등록합니다
			    kakao.maps.event.addListener(marker, 'click', function() {
			        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
			        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
			        infowindow.open(map, marker);
			    });
			}
		}
		
	</script>
	
</body>
</html>