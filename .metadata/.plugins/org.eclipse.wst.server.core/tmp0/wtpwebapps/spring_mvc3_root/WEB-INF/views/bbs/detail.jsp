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
#bbs table {
	width: 900px;
	margin: 0 auto;
	margin-top: 20px;
	border: 1px solid black;
	border-collapse: collapse;
	font-size: 14px;
}

#bbs table caption {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 10px;
}

#bbs table th {
	text-align: center;
	border: 1px solid black;
	padding: 4px 10px;
}

#bbs table td {
	text-align: left;
	border: 1px solid black;
	padding: 4px 10px;
}

.no {
	width: 15%
}

.subject {
	width: 30%
}

.writer {
	width: 20%
}

.reg {
	width: 20%
}

.hit {
	width: 15%
}

.title {
	background: lightsteelblue
}

.odd {
	background: silver
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
	function list_go(f) {
		f.action = "bbs_list.do";
		f.submit();
	}
	function delete_go(f) {
		f.action = "bbs_delete.do";
		f.submit();
	}
	function update_go(f) {
		f.action = "bbs_update.do";
		f.submit();
	}
	function comm_write(f) {
		f.action = "comm_write.do";
		f.submit();
	}
	function comm_delete(f) {
		f.action = "comm_delete.do";
		f.submit();
	}
</script>
</head>
<body>
	<div id="bbs">
		<form method="post">
			<table summary="상세보기">
				<caption>상세보기</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td>${bvo.subject }</td>
					</tr>
					<tr>
						<th>이름:</th>
						<td>${bvo.writer }</td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea id="content" cols="50" rows="8">${bvo.content }</textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<c:choose>
							<c:when test="${empty bvo.f_name}">
								<td><b>이전파일이 없습니다.</b></td>
							</c:when>
							<c:otherwise>
								<td><a href="bbs_down.do?f_name=${bvo.f_name}"> <img
										src="resources/upload/${bvo.f_name}" style="width: 80px;">
										<br>${bvo.f_name}
								</a></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td colspan="2"><input type="hidden" name="b_idx"
							value="${bvo.b_idx}"> <input type="hidden" name="cPage"
							value="${cPage}"> <input type="button" value="수정"
							onclick="update_go(this.form)" /> <input type="button"
							value="삭제" onclick="delete_go(this.form)" /> <input
							type="button" value="목록" onclick="list_go(this.form)" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<br>
	<br>
	<br>
	<%-- 댓글 입력 부분 --%>
	<div style="padding: 50px; width: 580px; margin: 0 auto;">
		<form method="post">
			<fieldset>
				<p>
					이름 : <input type="text" name="writer">
				</p>
				<p>
					내용 :
					<textarea rows="4" cols="40" name="content"></textarea>
				</p>
				<%-- 어떤글의 댓글인지 알아야하기 때문에 원글의 b_idx 가 필요하다. --%>
				<%-- **찜이나 장바구니를 하는 것도 비슷하다(어떤상품의~) --%>
				<input type="hidden" name="b_idx" value="${bvo.b_idx}"> <input
					type="button" value="댓글저장" onclick="comm_write(this.form)" />
			</fieldset>
		</form>
	</div>
	<%-- 댓글 출력 부분 --%>
	<div style="display: table; margin: 0 auto;">
		<%-- 댓글을 여러개 가지고 있을 수 있다. --%>
		<c:forEach var="k" items="${comm_list}">
			<div
				style="border: 1px solid #cc00cc; width: 400px; margin: 20px; padding: 20px;">
				<form method="post">
					<p>이름 : ${k.writer}</p>
					<p>내용 : ${k.content}</p>
					<p>날짜 : ${k.write_date.substring(0,10)}</p>
					<input type="hidden" name="b_idx" value="${k.b_idx}"> <input
						type="hidden" name="c_idx" value="${k.c_idx}"> <input
						type="button" value="댓글삭제" onclick="comm_delete(this.form)" />
					<%-- 실제는 로그인 성공 && 글쓴이만 댓글을 삭제할 수 있어야 한다. --%>
				</form>
			</div>
		</c:forEach>
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













