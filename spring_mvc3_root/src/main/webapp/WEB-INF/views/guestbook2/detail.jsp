<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>

<!-- 썸머노트 -->
<link rel="stylesheet" href="resources/css/summernote-lite.css">

<style type="text/css">
a {
	text-decoration: none;
}

table {
	width: 800px;
	border-collapse: collapse;
	text-align: center;
}

table, th, td {
	border: 1px solid black;
	padding: 3px
}

div {
	width: 800px;
	margin: auto;
	text-align: center;
}

/* 썸머노트 툴바 수정 */
.note-btn-group {
	width: auto;
}

.note-toolbar {
	width: auto;
}
</style>

<script type="text/javascript">
	function update_go(f) {
		f.action = "gb2_update.do";
		f.submit();
	}
	function delete_go(f) {
		f.action = "gb2_delete.do";
		f.submit();
	}
</script>
</head>
<body>
	<div>
		<h2>방명록 내용보기</h2>
		<hr>
		<p>
			[<a href="gb2_list.do">목록으로 이동</a>]
		</p>
		<form method="post">
			<table>
				<tr align="center">
					<td bgcolor="#99ccff">작성자</td>
					<td>${vo.name}</td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">제목</td>
					<td>${vo.subject}</td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">이메일</td>
					<td>${vo.email}</td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">첨부파일</td>
					<c:choose>
						<c:when test="${empty vo.f_name}">
							<td><b>이전파일이 없습니다.</b></td>
						</c:when>
						<c:otherwise>
							<td><a href="gb2_down.do?f_name=${vo.f_name}"> <img
									src="resources/upload/${vo.f_name}" style="width: 80px;">
									<br>${vo.f_name}
							</a></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr align="center">
					<td colspan="2"><textarea id="content" rows="10" cols="60"
							name="content" required>${vo.content}</textarea></td>
				</tr>
				<tfoot>
					<tr align="center">
						<td colspan="2"><input type="hidden" name="idx"
							value="${vo.idx}"> <input type="button" value="수정"
							onclick="update_go(this.form)">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="삭제"
							onclick="delete_go(this.form)"></td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js" 
			crossorigin="anonymous"></script>
	<script src="resources/js/summernote-lite.js"></script>
	<%-- 한국어 처리 --%>
	<script src="resources/js/lang/summernote-ko-KR.js"></script>
	
	<script type="text/javascript">
		$(function() {
			$("#content").summernote({
				lang : "ko-KR",
				height : 300 ,
				focus : true ,
				placeholder : "최대 3000자까지 쓸 수 있습니다." 
			});
			$("#content").summernote('disable');
		});
		
	</script>
	
</body>
</html>