<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#res").empty();
		//	파싱을 프론트에서 한거 (뽑아내기만 했음)
		/*
		$.ajax({
			url : "kakao_user.do",
			method : "post",
			dataType : "json",
			success : function(data) {
				let name = "";
				let email = "";
				
				//	파싱
				//name = data["properties"].nickname;
				//email = data["kakao_account"].email;
				
				//	다른방식
				$.each(data, function(index, obj) {
                    if(obj.nickname != undefined){
                     name = obj.nickname;
                    }
                    if(obj.email != undefined){
                     email = obj.email;
                    }
                })
				
				$("#res").append(name + " (" + email + ") 님 환영합니다.");
			},
			error : function() {
				alert("실패");
			}
		});
		*/
		
		//	파싱(쪼개서 정보를 가져오는 것) : 백에서 하기 => DB 에 저장하기 위해서
		//	백에서 이미 쪼개서 보냈기 때문에, 프론트에서는 파싱할 필요가 없다.
		$.ajax({
			url : "kakao_user2.do",
			method : "post",
			dataType : "text",
			success : function(data) {
				console.log(data);
				let users = data.split("/");
				$("#res").append(users[0] + "<br>" + users[1] + "(" + users[2] + ") 님 환영합니다.")
			},
			error : function() {
				alert("읽기실패");
			}
		});
		
	});
</script>
</head>
<body>

	<h2>SNS 로그인 결과보기</h2>
	
	<div id="res"></div>
	
	<%-- 카카오 로그아웃 --%>
	<%-- 내 애플리케이션 - 제품설정 - 카카오 로그인 - 고급 - 로그아웃 리다이렉트 uri 등록 --%>
	<%-- 방법은 문서 - 로그인 - REST API - 카카오계정과 함께 로그아웃 - GET 방식 - 주소 복사 --%>
	<%-- 필수 파라미터 : client_id , logout_redirect_uri --%>
	<a href="https://kauth.kakao.com/oauth/logout
	?client_id=84f22c8078987862ac7f5051394c4959
	&logout_redirect_uri=http://localhost:8090/kakaologout.do">
		로그아웃
	</a>
	

</body>
</html>