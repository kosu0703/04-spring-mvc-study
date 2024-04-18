<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 받기</title>
</head>
<body>

	<h2>인증번호를 받을 이메일을 넣어주세요</h2>
	<form action="email_send_ok.do" method="post">
		 <input type="email" name="email" title="이메일 양식"
            	pattern="[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.]+[a-zA-Z]+[.]*[a-zA-Z]*">
		<input type="submit" value="인증번호 보내기">
	</form>
	
</body>
</html>