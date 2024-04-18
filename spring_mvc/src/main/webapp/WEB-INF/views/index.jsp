<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function f_up(f) {
		f.submit();
	}
	function oracle_list() {
		location.href = "oracle_list.do";	
	}
	function maria_list() {
		location.href = "maria_list.do";	
	}
	function guestbook_list() {
		location.href = "guestbook_list.do";	
	}
</script>
</head>
<body>
	<h1>first spring mvc</h1>
	<!-- 확장자가 .do 이므로 web.xml 에서 지정한 servlet-context.xml 로 간다.(디스패쳐) -->
	<h2><a href="start1.do">start1</a></h2>
	<h2><a href="start2.do">start2</a></h2>
	<h2><a href="start3.do">start3</a></h2>
	<h2><a href="start4.do">start4</a></h2>
	<h2><a href="start5.do">start5</a></h2>
	<h2><a href="start6.do">start6</a></h2>
	<hr>
	<h2><a href="hello.do">인사하기</a></h2>
	<hr>
	<form action="calc.do" method="post">
		<p>수1 : <input type="number" size="15" name="s1" required></p>
		<p>수2 : <input type="number" size="15" name="s2" required></p>
		<p>연산자 : 
			<input type="radio" name="op" value="+" checked>+
			<input type="radio" name="op" value="-">-
			<input type="radio" name="op" value="*">*
			<input type="radio" name="op" value="/">/
		</p>
		<p>취미 : 
			<input type="checkbox" name="hobby" value="축구">축구
			<input type="checkbox" name="hobby" value="야구">야구
			<input type="checkbox" name="hobby" value="농구">농구
			<input type="checkbox" name="hobby" value="배구">배구
		</p>
		<input type="submit" value="보내기">
		<input type="hidden" name="cPage" value="2"> 
	</form>
	<hr>
	<h2>그림보기</h2>
	<img src="/resources/images/bear.jpg" style="width: 100px;"><br>
	<img src="resources/images/bear.jpg" style="width: 100px;"><br>
	<img src='<c:url value="/resources/images/bear.jpg" />' style="width: 100px;"><br>
	<img src='<c:url value="resources/images/bear.jpg" />' style="width: 100px;"><br>
	<hr>
	<!-- jsp 에서는 cos 라이브러리 사용 -->
	<!-- spring 에서 라이브러리 관리는 빌드(메이븐)가 한다.(pom.xml) -->
	<!-- mvnrepository 사이트에서 cos 를 찾아서 pom.xml 에 붙여넣기한다. -->
	<h2>파일업로드-1</h2>
	<form action="f_up.do" method="post" enctype="multipart/form-data">
		<p>올린사람 : <input type="text" name="name"></p>
		<p>파일 : <input type="file" name="f_name"></p>
		<p><input type="submit" value="업로드"></p>
	</form>
	<h2>파일업로드-2</h2>
	<form action="f_up.do" method="post" enctype="multipart/form-data">
		<p>올린사람 : <input type="text" name="name"></p>
		<p>파일 : <input type="file" name="f_name"></p>
		<p><input type="button" value="업로드" onclick="f_up(this.form)"></p>
	</form>
	<hr>
	<h2>파일업로드-3</h2>
	<form action="f_up2.do" method="post" enctype="multipart/form-data">
		<p>올린사람 : <input type="text" name="name"></p>
		<p>파일 : <input type="file" name="f_name"></p>
		<p><input type="submit" value="업로드2"></p>
	</form>
	<hr>
	<button onclick="oracle_list()">오라클 UserMembers 테이블 list 보기</button>
	<button onclick="maria_list()">마리아 UserMembers 테이블 list 보기</button>
	<hr>
	<button onclick="guestbook_list()">오라클 GuestBook 테이블 list 보기</button>
	<hr>
</body>
</html>





