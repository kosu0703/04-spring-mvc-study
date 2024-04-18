<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 썸머노트 -->
<link rel="stylesheet" href="resources/css/summernote-lite.css">

<style type="text/css">
tr {
	text-align: center;
	padding: 4px 10px;
	background-color: #F6F6F6;
}

th {
	width: 120px;
	text-align: center;
	padding: 4px 10px;
	background-color: #B2CCFF;
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
	function board_list(f) {
		f.action = "board_list.do";
		f.submit();
	}
	function ans_write(f) {
		f.action = "ans_write.do";
		f.submit();
	}
	function board_update(f) {
		f.action = "board_update.do";
		f.submit();
	}
	function board_delete(f) {
		f.action = "board_delete.do";
		f.submit();
	}
</script>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
		<table style="margin: 0 auto; width: 900px;">
			<tbody>
				<tr>
					<th bgcolor="#B2EBF4">제목</th>
					<td>${bovo.title}</td>
				</tr>
				<tr>
					<th bgcolor="#B2EBF4">작성자</th>
					<td>${bovo.writer}</td>
				</tr>
				<tr>
					<th bgcolor="#B2EBF4">첨부파일</th>
					<c:choose>
						<c:when test="${empty bovo.f_name}">
							<td><b>첨부파일없음</b></td>
						</c:when>
						<c:otherwise>
							<td><a href="board_down.do?f_name=${bovo.f_name}"> <img
									src="resources/upload/${bovo.f_name}" style="width: 80px;">
							</a> <br>${bovo.f_name}</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th bgcolor="#B2EBF4">날짜</th>
					<td>${bovo.regdate.substring(0,10)}</td>
				</tr>
				<tr>
					<th bgcolor="#B2EBF4">내용</th>
					<td><textarea id="content" cols="50" rows="8">${bovo.content}</textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2"><input type="hidden" name="bo_idx"
						value="${bovo.bo_idx}"> <input type="hidden" name="cPage"
						value="${cPage}"> <input type="button" value="답글"
						onclick="ans_write(this.form)"> &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="수정" onclick="board_update(this.form)">
						&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="삭제"
						onclick="board_delete(this.form)">
						&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="목록"
						onclick="board_list(this.form)"></td>
				</tr>
			</tfoot>
		</table>
	</form>
	
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